package school.sptech.se_vira.mapper;

import java.util.List;

import school.sptech.se_vira.dto.StatusCountDto;
import school.sptech.se_vira.dto.TaskRequestDto;
import school.sptech.se_vira.dto.TaskResponseDto;
import school.sptech.se_vira.model.Task;
import school.sptech.se_vira.model.TaskStatus;

public class TaskMapper {

    public TaskMapper() {
    }
    
    public static Task toEntity(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.title());
        task.setDescription(taskRequestDto.description());
        task.setDueDate(taskRequestDto.dueDate());
        task.setStatus(taskRequestDto.status());
        return task;
    }

    public static TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }

    public static List<TaskResponseDto> toResponseDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::toResponseDto)
                .toList();
    }

    public static StatusCountDto toStatusCount(TaskStatus status, Long count) {
        return new StatusCountDto(status, count);
    }

}
