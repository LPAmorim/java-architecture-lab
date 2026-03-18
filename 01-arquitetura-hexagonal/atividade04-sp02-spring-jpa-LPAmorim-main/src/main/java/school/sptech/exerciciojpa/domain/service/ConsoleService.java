package school.sptech.exerciciojpa.domain.service;

import school.sptech.exerciciojpa.domain.model.Console;
import java.util.List;

import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleRequestDto;
import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleResponseDto;
import school.sptech.exerciciojpa.adapter.in.web.mapper.ConsoleWebMapper;
import school.sptech.exerciciojpa.domain.exception.ConsoleNotFoundException;
import school.sptech.exerciciojpa.domain.exception.DuplicateConsoleException;
import school.sptech.exerciciojpa.domain.port.in.ConsoleUseCase;
import school.sptech.exerciciojpa.domain.port.out.ConsoleRepositoryPort;

/**
 * Serviço de domínio — contém as regras de negócio.
 * Implementa o Port IN (ConsoleUseCase) e usa o Port OUT (ConsoleRepositoryPort).
 *
 * IMPORTANTE: Esta classe NÃO tem @Service do Spring.
 * Quem faz a injeção é o BeanConfig, mantendo o domínio livre de anotações de framework.
 *
 * SOBRE VALIDAÇÃO:
 * Este projeto NÃO usa Jakarta Bean Validation (spring-boot-starter-validation).
 * Por isso, a validação dos campos é feita MANUALMENTE no método validateConsoleRequest().
 *
 * Se tivesse Jakarta, a validação ficaria no DTO com anotações (@NotBlank, @Positive etc.)
 * e o Spring validaria automaticamente com @Valid no Controller.
 * Sem Jakarta, a responsabilidade cai no Service — que é a próxima camada depois do Controller.
 */
public class ConsoleService implements ConsoleUseCase {

    private final ConsoleRepositoryPort repositoryPort;

    public ConsoleService(ConsoleRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void delete(Integer id) {
        if (repositoryPort.findById(id).isEmpty()) {
            throw new ConsoleNotFoundException(id);
        }
        repositoryPort.deleteById(id);
    }

    @Override
    public List<ConsoleResponseDto> findAll() {
        return repositoryPort.findAll().stream()
                .map(ConsoleWebMapper::toResponse)
                .toList();
    }

    @Override
    public ConsoleResponseDto findById(Integer id) {
        return repositoryPort.findById(id)
                .map(ConsoleWebMapper::toResponse)
                .orElseThrow(() -> new ConsoleNotFoundException(id));
    }

    @Override
    public ConsoleResponseDto register(ConsoleRequestDto request) {
        /*
         * Validação manual dos campos obrigatórios.
         *
         * Com Jakarta Bean Validation, isso NÃO existiria aqui.
         * No lugar, o DTO teria anotações e o Controller usaria @Valid:
         *
         *   public record ConsoleRequestDto(
         *       @NotBlank(message = "Nome é obrigatório") String nome,
         *       @NotBlank(message = "Fabricante é obrigatório") String fabricante,
         *       @NotNull(message = "Geração é obrigatória") Integer geracao,
         *       @NotNull(message = "Preço é obrigatório") @Positive(message = "Preço > 0") Double preco
         *   ) {}
         *
         * E o Spring lançaria MethodArgumentNotValidException automaticamente,
         * capturada pelo GlobalExceptionHandler → retorno 400.
         */
        validateConsoleRequest(request);

        validateUniqueNomeAndFabricante(request.nome(), request.fabricante());

        Console console = ConsoleWebMapper.toEntity(request);
        Console saved = repositoryPort.save(console);
        return ConsoleWebMapper.toResponse(saved);
    }

    @Override
    public ConsoleResponseDto update(Integer id, ConsoleRequestDto request) {
        Console existingConsole = repositoryPort.findById(id)
                .orElseThrow(() -> new ConsoleNotFoundException(id));

        // Validação manual 
        validateConsoleRequest(request);

        validateUniqueNomeAndFabricante(request.nome(), request.fabricante());

        existingConsole.setNome(request.nome())
                .setFabricante(request.fabricante())
                .setGeracao(request.geracao())
                .setPreco(request.preco());

        Console updated = repositoryPort.save(existingConsole);
        return ConsoleWebMapper.toResponse(updated);
    }

    @Override
    public List<ConsoleResponseDto> findByNomeAndFabricante(String nome, String fabricante) {
        boolean hasNome = nome != null && !nome.isBlank();
        boolean hasFabricante = fabricante != null && !fabricante.isBlank();

        List<Console> consoles;
        if (hasNome && hasFabricante) {
            consoles = repositoryPort.findByNomeAndFabricante(nome, fabricante);
        } else if (hasNome) {
            consoles = repositoryPort.findByNome(nome);
        } else if (hasFabricante) {
            consoles = repositoryPort.findByFabricante(fabricante);
        } else {
            consoles = repositoryPort.findAll();
        }

        return consoles.stream()
                .map(ConsoleWebMapper::toResponse)
                .toList();
    }

    // ======================== VALIDAÇÕES PRIVADAS ========================

    /**
     * Validação manual dos campos do request.
     *
     * SEM Jakarta Bean Validation, precisamos checar nós mesmos.
     * Lança IllegalArgumentException (capturada pelo GlobalExceptionHandler → 400).
     *
     * COM Jakarta, bastaria anotar os campos no DTO e usar @Valid no Controller.
     * Cada anotação equivalente está nos comentários abaixo:
     */
    private void validateConsoleRequest(ConsoleRequestDto request) {
        // Equivale a @NotBlank no campo "nome"
        if (request.nome() == null || request.nome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Equivale a @NotBlank no campo "fabricante"
        if (request.fabricante() == null || request.fabricante().isBlank()) {
            throw new IllegalArgumentException("Fabricante é obrigatório");
        }

        // Equivale a @NotNull no campo "geracao"
        if (request.geracao() == null) {
            throw new IllegalArgumentException("Geração é obrigatória");
        }

        // Equivale a @NotNull + @Positive no campo "preco"
        if (request.preco() == null || request.preco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }

    /**
     * Regra de negócio: não pode existir dois consoles com mesmo nome e fabricante.
     * Isso é regra de NEGÓCIO, não validação de formato — por isso fica no Service
     * mesmo que tivesse Jakarta Bean Validation.
     */
    private void validateUniqueNomeAndFabricante(String nome, String fabricante) {
        if (repositoryPort.existsByNomeAndFabricante(nome, fabricante)) {
            throw new DuplicateConsoleException(nome, fabricante);
        }
    }
}
