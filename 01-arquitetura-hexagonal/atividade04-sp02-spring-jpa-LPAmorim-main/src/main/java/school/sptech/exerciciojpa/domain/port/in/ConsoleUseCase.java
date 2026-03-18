package school.sptech.exerciciojpa.domain.port.in;

import java.util.List;

import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleRequestDto;
import school.sptech.exerciciojpa.adapter.in.web.dto.ConsoleResponseDto;

/**
 * Port IN — interface que define os casos de uso do domínio.
 * O Controller (adapter in) depende DESTA interface, nunca da implementação concreta.
 *
 * Quem implementa é o ConsoleService (domain/service).
 * Quem instancia e injeta é o BeanConfig.
 *
 */
public interface ConsoleUseCase {

    List<ConsoleResponseDto> findAll();

    ConsoleResponseDto findById(Integer id);

    ConsoleResponseDto register(ConsoleRequestDto request);

    ConsoleResponseDto update(Integer id, ConsoleRequestDto request);

    void delete(Integer id);

    List<ConsoleResponseDto> findByNomeAndFabricante(String nome, String fabricante);
}
