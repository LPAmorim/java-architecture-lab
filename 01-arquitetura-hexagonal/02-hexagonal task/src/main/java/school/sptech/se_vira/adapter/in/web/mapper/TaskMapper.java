package school.sptech.se_vira.adapter.in.web.mapper;

import school.sptech.se_vira.adapter.in.web.dto.StatusCountDto;
import school.sptech.se_vira.adapter.in.web.dto.TaskRequestDto;
import school.sptech.se_vira.adapter.in.web.dto.TaskResponseDto;
import school.sptech.se_vira.domain.model.Task;
import school.sptech.se_vira.domain.model.TaskStatus;

import java.util.List;

public class TaskMapper {

    public static TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }

    public static Task toEntity(TaskRequestDto requestDto) {
        return new Task(
                null,
                requestDto.title(),
                requestDto.description(),
                requestDto.dueDate(),
                requestDto.status()
        );
    }

    public static List<TaskResponseDto> toResponseDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::toResponseDto)
                .toList();
    }   

    public static StatusCountDto toStatusCount(TaskStatus status, long count) {
        return new StatusCountDto(status, count);
    }
}
