package school.sptech.se_vira.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.se_vira.domain.model.TaskStatus;

import java.time.LocalDate;

public record TaskRequestDto(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        String description,

        @NotNull
        LocalDate dueDate,

        @NotNull
        TaskStatus status
) {
}