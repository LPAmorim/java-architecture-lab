package school.sptech.se_vira.adapter.in.web.dto;

import java.time.LocalDate;

import school.sptech.se_vira.domain.model.TaskStatus;
public record TaskResponseDto(
        Integer id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status
) {
}
