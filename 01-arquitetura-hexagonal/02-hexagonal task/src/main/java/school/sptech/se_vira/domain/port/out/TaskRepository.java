package school.sptech.se_vira.domain.port.out;

import school.sptech.se_vira.domain.model.Task;
import school.sptech.se_vira.domain.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();
    
    Optional<Task> findById(Integer id);
    
    Task save(Task task);

    void deleteById(Integer id);

    Optional<Task> findByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndIdNot(String title, Integer id);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleLikeIgnoreCase(String keyword);

    long countByStatus(TaskStatus status);
}
