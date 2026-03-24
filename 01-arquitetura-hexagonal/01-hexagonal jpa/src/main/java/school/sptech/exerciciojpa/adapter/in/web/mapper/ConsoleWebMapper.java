package school.sptech.exerciciojpa.adapter.in.web.mapper;

import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleRequestDto;
import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleResponseDto;
import school.sptech.exerciciojpa.domain.model.Console;

/**
 * Mapper Web — converte entre DTOs (camada web) e modelo de domínio.
 *
 * Cada fronteira do hexágono tem seu próprio mapper:
 *   - ConsoleWebMapper: DTO ↔ Domain (fronteira de entrada HTTP)
 *   - ConsolePersistenceMapper: Domain ↔ JpaEntity (fronteira de saída BD)
 *
 * Construtor privado porque só tem métodos estáticos (utility class).
 */
public class ConsoleWebMapper {

    private ConsoleWebMapper() {
    }

    /**
     * Converte ConsoleRequestDto (DTO entrada) → Console (modelo de domínio).
     * Chamado pelo Service ao receber um request de criação/atualização.
     */
    public static Console toEntity(ConsoleRequestDto request) {
        Console console = new Console();
        console.setNome(request.nome());
        console.setFabricante(request.fabricante());
        console.setGeracao(request.geracao());
        console.setPreco(request.preco());
        return console;
    }

    /**
     * Converte Console (modelo de domínio) → ConsoleResponseDto (DTO saída).
     * Chamado pelo Service antes de retornar ao Controller.
     */
    public static ConsoleResponseDto toResponse(Console console) {
        return new ConsoleResponseDto(
                console.getId(),
                console.getNome(),
                console.getFabricante(),
                console.getGeracao(),
                console.getPreco()
        );
    }
}
