package school.sptech.se_vira.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import school.sptech.se_vira.repository.TaskRepository;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StepContext ctx;

    @Before
    public void limparBase() {
        taskRepository.deleteAll();
    }

    // -------------------------------------------------------------------------
    // GIVEN
    // -------------------------------------------------------------------------

    @Given("que não existe nenhuma tarefa cadastrada")
    public void quenaoExisteNenhumaTarefaCadastrada() {
        taskRepository.deleteAll();
    }

    @Given("que existem {int} tarefas cadastradas")
    public void queExistemTarefasCadastradas(int quantidade) throws Exception {
        for (int i = 1; i <= quantidade; i++) {
            criarTarefa("Tarefa " + i, "Descrição " + i, "2026-04-01", "TODO");
        }
    }

    @Given("que existe uma tarefa com título {string}, descrição {string}, data {string} e status {string}")
    public void queExisteUmaTarefa(String titulo, String descricao, String data, String status) throws Exception {
        MvcResult result = criarTarefa(titulo, descricao, data, status);
        Map<?, ?> body = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        ctx.setLastCreatedId(((Number) body.get("id")).intValue());
    }

    @And("existe outra tarefa com título {string}, descrição {string}, data {string} e status {string}")
    public void existeOutraTarefa(String titulo, String descricao, String data, String status) throws Exception {
        criarTarefa(titulo, descricao, data, status);
    }

    // -------------------------------------------------------------------------
    // WHEN
    // -------------------------------------------------------------------------

    @When("envio uma requisição POST para {string} com título {string}, descrição {string}, data {string} e status {string}")
    public void envioPost(String path, String titulo, String descricao, String data, String status) throws Exception {
        String payload = buildPayload(titulo, descricao, data, status);
        ctx.setResult(mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)));
    }

    @When("envio uma requisição GET para {string}")
    public void envioGet(String path) throws Exception {
        ctx.setResult(mockMvc.perform(get(path)));
    }

    @When("envio uma requisição GET para {string} com o id da tarefa criada")
    public void envioGetComId(String pathTemplate) throws Exception {
        String path = pathTemplate.replace("{id}", ctx.getLastCreatedId().toString());
        ctx.setResult(mockMvc.perform(get(path)));
    }

    @When("envio uma requisição PUT para {string} com título {string}, descrição {string}, data {string} e status {string}")
    public void envioPut(String path, String titulo, String descricao, String data, String status) throws Exception {
        String resolvedPath = path.contains("{id}")
                ? path.replace("{id}", ctx.getLastCreatedId().toString())
                : path;
        String payload = buildPayload(titulo, descricao, data, status);
        ctx.setResult(mockMvc.perform(put(resolvedPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)));
    }

    @When("envio uma requisição DELETE para {string} com o id da tarefa criada")
    public void envioDeleteComId(String pathTemplate) throws Exception {
        String path = pathTemplate.replace("{id}", ctx.getLastCreatedId().toString());
        ctx.setResult(mockMvc.perform(delete(path)));
    }

    @When("envio uma requisição DELETE para {string}")
    public void envioDelete(String path) throws Exception {
        ctx.setResult(mockMvc.perform(delete(path)));
    }

    // -------------------------------------------------------------------------
    // THEN / AND
    // -------------------------------------------------------------------------

    @Then("o código de resposta deve ser {int}")
    public void oCodigoDeRespostaDeveSer(int status) throws Exception {
        ctx.getResult().andExpect(status().is(status));
    }

    @And("o corpo deve conter id gerado")
    public void oCorpoDeveConterIdGerado() throws Exception {
        ctx.getResult().andExpect(jsonPath("$.id").isNumber());
    }

    @And("o corpo deve conter título {string}")
    public void oCorpoDeveConterTitulo(String titulo) throws Exception {
        ctx.getResult().andExpect(jsonPath("$.title").value(titulo));
    }

    @And("o corpo deve conter status {string}")
    public void oCorpoDeveConterStatus(String status) throws Exception {
        ctx.getResult().andExpect(jsonPath("$.status").value(status));
    }

    @And("o corpo deve ser uma lista com {int} itens")
    public void oCorpoDeveSerUmaListaCom(int quantidade) throws Exception {
        ctx.getResult().andExpect(jsonPath("$", hasSize(quantidade)));
    }

    @And("o corpo deve conter o campo de erro {string}")
    public void oCorpoDeveConterOCampoDeErro(String campo) throws Exception {
        ctx.getResult().andExpect(jsonPath("$.errors." + campo).exists());
    }

    @And("o corpo deve conter a mensagem de erro")
    public void oCorpoDeveConterAMensagemDeErro() throws Exception {
        ctx.getResult().andExpect(jsonPath("$.message").exists());
    }

    @And("o corpo deve conter contagem para os {int} status")
    public void oCorpoDeveConterContagemParaOsStatus(int quantidade) throws Exception {
        ctx.getResult().andExpect(jsonPath("$", hasSize(quantidade)));
        ctx.getResult().andExpect(jsonPath("$[*].status", hasItems("TODO", "DOING", "DONE")));
        ctx.getResult().andExpect(jsonPath("$[*].count", everyItem(notNullValue())));
    }

    // -------------------------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------------------------

    private MvcResult criarTarefa(String titulo, String descricao, String data, String status) throws Exception {
        return mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildPayload(titulo, descricao, data, status)))
                .andReturn();
    }

    private String buildPayload(String titulo, String descricao, String data, String status) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("title", titulo);
        map.put("description", descricao);
        map.put("dueDate", data);
        map.put("status", status);
        return objectMapper.writeValueAsString(map);
    }
}
