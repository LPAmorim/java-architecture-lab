package school.sptech.se_vira.adapter.out.persister.mapper;

import school.sptech.se_vira.adapter.out.persister.entity.TaskEntity;
import school.sptech.se_vira.domain.model.Task;

public class TaskPersiterMapper {
    
    private TaskPersiterMapper() { }

    public static TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }

    public static Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate(),
                entity.getStatus()
        );
    }
}
