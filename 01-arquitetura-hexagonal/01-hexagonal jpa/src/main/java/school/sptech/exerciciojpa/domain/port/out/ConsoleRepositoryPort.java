package school.sptech.exerciciojpa.domain.port.out;

import school.sptech.exerciciojpa.domain.model.Console;
import java.util.List;
import java.util.Optional;

/**
 * Port OUT — interface que o domínio PRECISA da infraestrutura.
 * O adapter out (JpaConsoleRepository) implementa esta interface.
 *
 * O Service nunca sabe se por trás tem JPA, MongoDB, API REST ou arquivo CSV.
 * Ele só conhece esta interface — isso é inversão de dependência (DIP - SOLID).
 */
public interface ConsoleRepositoryPort {
    
    Console save(Console console);

    Optional<Console> findById(Integer id);

    List<Console> findAll();

    void deleteById(Integer id);

    boolean existsByNomeAndFabricante(String nome, String fabricante);

    List<Console> findByNomeAndFabricante(String nome, String fabricante);

    List<Console> findByNome(String nome);

    List<Console> findByFabricante(String fabricante);
}
