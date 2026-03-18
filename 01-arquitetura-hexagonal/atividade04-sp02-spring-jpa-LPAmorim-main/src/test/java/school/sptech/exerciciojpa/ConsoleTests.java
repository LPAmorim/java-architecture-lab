package school.sptech.exerciciojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.exerciciojpa.provider.InvalidConsoleProvider;
import school.sptech.exerciciojpa.util.FileUtil;
import tools.jackson.databind.ObjectMapper;

@DBRider
@DisplayName("Console")
@SpringBootTest(classes = ExerciciojpaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DBUnit(cacheConnection = false, alwaysCleanAfter = true, raiseExceptionOnCleanUp = true)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
public class ConsoleTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("GET /consoles")
    class GetConsoles {

        @Test
        @DisplayName("GET /consoles deve retornar lista de consoles corretamente")
        @DataSet(value = "datasets/input/consoles.json")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturnListOfConsolesCorrectly() throws Exception {
            var result = mockMvc.perform(get("/consoles"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/get-consoles.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("GET /consoles deve retornar 204 quando não houver consoles no banco de dados")
        @DataSet(value = "datasets/input/empty-consoles.json")
        @ExpectedDataSet(value = "datasets/expected/empty-consoles.json")
        void shouldReturn204WhenDatabaseIsEmpty() throws Exception {
            mockMvc.perform(get("/consoles")).andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("GET /consoles/{id}")
    @DataSet(value = "datasets/input/consoles.json")
    class GetConsoleById {

        @Test
        @DisplayName("GET /consoles/{id} deve retornar o console pelo id corretamente")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturnConsoleByIdCorrectly() throws Exception {
            var result = mockMvc.perform(get("/consoles/8"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/console-by-id-8.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("GET /consoles/{id} deve retornar 404 quando o console não existir")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturn404WhenConsoleDoesNotExist() throws Exception {
            mockMvc.perform(get("/consoles/999")).andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /consoles")
    @DataSet(value = "datasets/input/consoles.json", executeScriptsBefore = "datasets/schema/set-auto-increment.sql")
    class PostConsole {

        @Test
        @DisplayName("POST /consoles deve criar console corretamente")
        @ExpectedDataSet(value = "datasets/expected/new-console.json", compareOperation = CompareOperation.CONTAINS)
        void shouldCreateConsole() throws Exception {
            var requestBody = FileUtil.readFile("/request/new-console.json");
            var result = mockMvc.perform(post("/consoles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/new-console.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("POST /consoles deve retornar 409 quando console já existir")
        @ExpectedDataSet(value = "datasets/expected/consoles.json")
        void shouldReturn409WhenConsoleAlreadyExists() throws Exception {
            var requestBody = FileUtil.readFile("/request/new-duplicated-console.json");
            mockMvc.perform(post("/consoles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isConflict());
        }

        @DisplayName("POST /consoles deve validar os campos")
        @ParameterizedTest(name = "{0}")
        @ArgumentsSource(InvalidConsoleProvider.class)
        @ExpectedDataSet(value = "datasets/expected/consoles.json")
        void shouldValidatePostConsole(String caso, String file) throws Exception {
            var requestBody = FileUtil.readFile(file);
            mockMvc.perform(post("/consoles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("PUT /consoles/{id}")
    @DataSet(value = "datasets/input/consoles.json")
    class PutConsole {

        @Test
        @DisplayName("PUT /consoles/{id} deve atualizar console corretamente")
        @ExpectedDataSet(value = "datasets/expected/update-console.json", compareOperation = CompareOperation.CONTAINS)
        void shouldUpdateConsole() throws Exception {
            var requestBody = FileUtil.readFile("/request/update-console.json");
            var result = mockMvc.perform(put("/consoles/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/update-console.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("PUT /consoles/{id} deve retornar 404 quando console não existir")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturn404WhenConsoleDoesNotExistOnUpdate() throws Exception {
            var requestBody = FileUtil.readFile("/request/update-console.json");
            mockMvc.perform(put("/consoles/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("PUT /consoles/{id} deve retornar 409 quando tentar atualizar para um console duplicado")
        @ExpectedDataSet(value = "datasets/expected/consoles.json")
        void shouldReturn409WhenUpdatingToDuplicateConsole() throws Exception {
            var requestBody = FileUtil.readFile("/request/update-duplicated-console.json");
            mockMvc.perform(put("/consoles/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isConflict());
        }

        @DisplayName("PUT /consoles/{id} deve validar os campos")
        @ParameterizedTest(name = "{0}")
        @ArgumentsSource(InvalidConsoleProvider.class)
        @ExpectedDataSet(value = "datasets/expected/consoles.json")
        void shouldValidatePutConsole(String caso, String file) throws Exception {
            var requestBody = FileUtil.readFile(file);
            mockMvc.perform(put("/consoles/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                  .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE /consoles/{id}")
    @DataSet(value = "datasets/input/consoles.json")
    class DeleteConsole {

        @Test
        @DisplayName("DELETE /consoles/{id} deve deletar console corretamente")
        @ExpectedDataSet(value = "datasets/expected/consoles-delete-8.json", compareOperation = CompareOperation.CONTAINS)
        void shouldDeleteConsole() throws Exception {
            mockMvc.perform(delete("/consoles/8"))
                  .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("DELETE /consoles/{id} deve retornar 404 quando console não existir")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturn404WhenConsoleDoesNotExistOnDelete() throws Exception {
            mockMvc.perform(delete("/consoles/999"))
                  .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /consoles/search")
    @DataSet(value = "datasets/input/consoles.json")
    class SearchConsoles {

        @Test
        @DisplayName("GET /consoles/search deve retornar consoles quando filtrar por nome")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldSearchByNomeContaining() throws Exception {
            var result = mockMvc.perform(get("/consoles/search")
                        .param("nome", "endo"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/search-consoles-by-nome.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("GET /consoles/search deve retornar consoles quando filtrar por fabricante ")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldSearchByFabricanteSony() throws Exception {
            var result = mockMvc.perform(get("/consoles/search")
                        .param("fabricante", "soft"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/search-consoles-by-fabricante.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("GET /consoles/search deve retornar consoles quando filtrar por nome e fabricante")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldSearchByNomeAndFabricante() throws Exception {
            var result = mockMvc.perform(get("/consoles/search")
                        .param("nome", "si")
                        .param("fabricante", "se"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            assertNotNull(responseBody);

            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile(
                  "/response/search-consoles-by-nome-and-fabricante.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }

        @Test
        @DisplayName("GET /consoles/search deve retornar 204 quando não encontrar consoles com os filtros")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturn204WhenNoConsolesFoundWithFilters() throws Exception {
            mockMvc.perform(get("/consoles/search")
                        .param("nome", "xyz")
                        .param("fabricante", "abc"))
                  .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("GET /consoles/search deve retornar todos os consoles quando não passar filtros")
        @ExpectedDataSet("datasets/expected/consoles.json")
        void shouldReturnAllConsolesWhenNoFilters() throws Exception {
            var result = mockMvc.perform(get("/consoles/search"))
                  .andExpect(status().isOk())
                  .andReturn();

            var responseBody = result.getResponse().getContentAsString();
            var root = objectMapper.readTree(responseBody);
            var expected = FileUtil.readFile("/response/get-consoles.json");
            var expectedRoot = objectMapper.readTree(expected);
            assertEquals(expectedRoot, root);
        }
    }
}


