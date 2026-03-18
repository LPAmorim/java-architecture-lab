package school.sptech.exerciciojpa.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import school.sptech.exerciciojpa.adapter.out.persistence.SpringDataConsoleRepository;
import school.sptech.exerciciojpa.adapter.out.persistence.mapper.ConsolePersistenceMapper;
import school.sptech.exerciciojpa.domain.model.Console;
import school.sptech.exerciciojpa.domain.port.out.ConsoleRepositoryPort;

/**
 * Adapter OUT — implementa o Port OUT usando Spring Data JPA.
 * O domínio (Service) não sabe que JPA existe — só conhece a interface ConsoleRepositoryPort.
 *
 * Fluxo: Service → ConsoleRepositoryPort → JpaConsoleRepository → SpringDataConsoleRepository → BD
 *
 * Se amanhã trocar para MongoDB, basta criar um MongoConsoleRepository
 * que implemente ConsoleRepositoryPort. O Service não muda.
 */
@Component
public class JpaConsoleRepository implements ConsoleRepositoryPort {

    private final SpringDataConsoleRepository repository;

    public JpaConsoleRepository(SpringDataConsoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByNomeAndFabricante(String nome, String fabricante) {
        return repository.existsByNomeAndFabricante(nome, fabricante);
    }

    @Override
    public List<Console> findAll() {
        return repository.findAll().stream()
                .map(ConsolePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Console> findByFabricante(String fabricante) {
        return repository.findByFabricanteContainingIgnoreCase(fabricante).stream()
                .map(ConsolePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Console> findById(Integer id) {
        return repository.findById(id)
                .map(ConsolePersistenceMapper::toDomain);
    }

    @Override
    public List<Console> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(ConsolePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Console> findByNomeAndFabricante(String nome, String fabricante) {
        return repository.findByNomeContainingIgnoreCaseAndFabricanteContainingIgnoreCase(nome, fabricante).stream()
                .map(ConsolePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Console save(Console console) {
        return ConsolePersistenceMapper.toDomain(
                repository.save(ConsolePersistenceMapper.toJpaEntity(console))
        );
    }
}
