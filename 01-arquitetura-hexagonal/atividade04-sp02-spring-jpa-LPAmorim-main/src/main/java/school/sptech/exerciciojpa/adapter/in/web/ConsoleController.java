package school.sptech.exerciciojpa.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleRequestDto;
import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleResponseDto;
import school.sptech.exerciciojpa.domain.port.in.ConsoleUseCase;

import java.util.List;

/**
 * Adapter IN (Web) — recebe requisições HTTP e delega para o domínio via porta de entrada.
 *
 * PRINCÍPIO: Controller LIMPO — sem regras de negócio, sem acesso ao banco, sem try/catch.
 * Quem cuida dos erros é o GlobalExceptionHandler (handler centralizado).
 *
 * O controller depende de ConsoleUseCase (interface/porta), nunca da implementação concreta.
 * Isso permite trocar a implementação do service sem mexer aqui.
 *
 * DICA: Se estivesse usando Jakarta Bean Validation (dependência spring-boot-starter-validation),
 * bastaria adicionar @Valid antes de @RequestBody para ativar as validações automáticas
 * do DTO (ex.: @NotBlank, @Positive). Sem Jakarta, a validação é feita manualmente no Service.
 */
@RestController
@RequestMapping("/consoles")
public class ConsoleController {

    private final ConsoleUseCase service;

    public ConsoleController(ConsoleUseCase service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConsoleResponseDto>> list() {
        List<ConsoleResponseDto> listResponse = service.findAll();
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsoleResponseDto> buscarPorId(@PathVariable Integer id) {
        ConsoleResponseDto response = service.findById(id);
        return ResponseEntity.ok(response);
    }
    /*
     * DICA: Com Jakarta Bean Validation, a assinatura ficaria assim:
     *   public ResponseEntity<ConsoleResponseDto> cadastro(@Valid @RequestBody ConsoleRequestDto requestDto)
     * O @Valid faz o Spring validar automaticamente os campos anotados no DTO (@NotBlank, @Positive etc).
     * Se algum campo for inválido, o Spring lança MethodArgumentNotValidException
     * que é capturada pelo GlobalExceptionHandler e retorna 400 (Bad Request).
     */

    @PostMapping
    public ResponseEntity<ConsoleResponseDto> cadastro(@RequestBody ConsoleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsoleResponseDto> atualizar(@PathVariable Integer id,
                                                        @RequestBody ConsoleRequestDto requestDto) {
        ConsoleResponseDto response = service.update(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConsoleResponseDto>> buscarPorNomeFabricante(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String fabricante) {
        List<ConsoleResponseDto> listResponse = service.findByNomeAndFabricante(nome, fabricante);
        
        if (listResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listResponse);
    }
}
