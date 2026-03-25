package school.sptech.se_vira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.se_vira.model.Task;
import school.sptech.se_vira.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByTitleIgnoreCase(String title);

    boolean existsByTitleContainingIgnoreCaseAndIdNot(String title, Integer id);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    long countByStatus(TaskStatus status);
}
