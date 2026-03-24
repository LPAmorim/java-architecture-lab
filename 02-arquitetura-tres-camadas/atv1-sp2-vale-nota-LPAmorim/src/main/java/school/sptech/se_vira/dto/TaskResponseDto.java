package school.sptech.se_vira.dto;

import school.sptech.se_vira.model.TaskStatus;

import java.time.LocalDate;

public record TaskResponseDto(
        Integer id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status
) {
}
