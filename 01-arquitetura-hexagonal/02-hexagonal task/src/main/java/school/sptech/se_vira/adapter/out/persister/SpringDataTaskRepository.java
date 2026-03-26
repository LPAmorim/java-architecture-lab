package school.sptech.se_vira.adapter.out.persister;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school.sptech.se_vira.adapter.out.persister.entity.TaskEntity;
import school.sptech.se_vira.domain.model.TaskStatus;

@Repository
public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, Integer> {
 
    Optional<TaskEntity> findByTitleIgnoreCase(String title);

    boolean existsByTitleContainingIgnoreCaseAndIdNot(String title, Integer id);

    List<TaskEntity> findByStatus(TaskStatus status);

    List<TaskEntity> findByTitleContainingIgnoreCase(String keyword);

    long countByStatus(TaskStatus status);
    
}   
