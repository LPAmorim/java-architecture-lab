package school.sptech.se_vira.dto;

import school.sptech.se_vira.model.TaskStatus;

import java.time.LocalDate;

public class TaskResponseDto {

    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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
