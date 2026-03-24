package school.sptech.exerciciojpa.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import school.sptech.exerciciojpa.adapter.out.persistence.entity.ConsoleJpaEntity;

public interface SpringDataConsoleRepository extends JpaRepository<ConsoleJpaEntity, Integer> {

    List<ConsoleJpaEntity> findByNomeContainingIgnoreCaseAndFabricanteContainingIgnoreCase(String nome, String fabricante);

    List<ConsoleJpaEntity> findByNomeContainingIgnoreCase(String nome);

    List<ConsoleJpaEntity> findByFabricanteContainingIgnoreCase(String fabricante);

    boolean existsByNomeAndFabricante(String nome, String fabricante);


}
