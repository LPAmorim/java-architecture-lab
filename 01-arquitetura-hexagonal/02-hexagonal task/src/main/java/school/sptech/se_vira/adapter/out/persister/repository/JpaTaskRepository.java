package school.sptech.se_vira.adapter.out.persister.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import school.sptech.se_vira.adapter.out.persister.SpringDataTaskRepository;
import school.sptech.se_vira.adapter.out.persister.mapper.TaskPersiterMapper;
import school.sptech.se_vira.domain.model.Task;
import school.sptech.se_vira.domain.model.TaskStatus;
import school.sptech.se_vira.domain.port.out.TaskRepository;

@Component
public class JpaTaskRepository implements TaskRepository {

    private final SpringDataTaskRepository springDataTaskRepository;

    public JpaTaskRepository(SpringDataTaskRepository springDataTaskRepository) {
        this.springDataTaskRepository = springDataTaskRepository;
    }

    @Override
    public long countByStatus(TaskStatus status) {
        return springDataTaskRepository.countByStatus(status);
    }

    @Override
    public void deleteById(Integer id) {
        springDataTaskRepository.deleteById(id);
        
    }

    @Override
    public boolean existsByTitleIgnoreCaseAndIdNot(String title, Integer id) {
        return springDataTaskRepository.existsByTitleContainingIgnoreCaseAndIdNot(title, id);
    }

    @Override
    public List<Task> findAll() {
        return springDataTaskRepository.findAll().stream()
                .map(TaskPersiterMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return springDataTaskRepository.findById(id)
                .map(TaskPersiterMapper::toDomain);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return springDataTaskRepository.findByStatus(status).stream()
                .map(TaskPersiterMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> findByTitleIgnoreCase(String title) {
        return springDataTaskRepository.findByTitleIgnoreCase(title)
                .map(TaskPersiterMapper::toDomain);
    }

    @Override
    public List<Task> findByTitleLikeIgnoreCase(String keyword) {
        return springDataTaskRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(TaskPersiterMapper::toDomain)
                .toList();
    }

    @Override
    public Task save(Task task) {
        return TaskPersiterMapper.toDomain(
                springDataTaskRepository.save(TaskPersiterMapper.toEntity(task))
        );
    }
    
    
}
