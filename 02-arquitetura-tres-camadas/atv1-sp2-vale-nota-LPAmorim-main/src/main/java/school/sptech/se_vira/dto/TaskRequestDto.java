package school.sptech.se_vira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.se_vira.model.TaskStatus;

import java.time.LocalDate;

public record TaskRequestDto(
        @NotBlank(message = "O título não pode ser nulo ou em branco") 
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String title,

        @NotBlank(message = "A descrição não pode ser nula ou em branco")
        String description,

        @NotNull(message = "A data de vencimento não pode ser nula")
        LocalDate dueDate,

        @NotNull(message = "O status não pode ser nulo")
        TaskStatus status
) {
}