package school.sptech.se_vira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.se_vira.model.TaskStatus;

import java.time.LocalDate;

public class TaskRequestDto {

    @NotBlank(message = "O título não pode ser nulo ou em branco")
    @Size(max = 10, message = "O título deve ter no máximo 100 caracteres")
    private String title;

    @NotBlank(message = "A descrição não pode ser nula ou em branco")
    private String description;

    @NotBlank
    private LocalDate dueDate;

    @NotNull(message = "O status não pode ser nulo")
    private TaskStatus status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
