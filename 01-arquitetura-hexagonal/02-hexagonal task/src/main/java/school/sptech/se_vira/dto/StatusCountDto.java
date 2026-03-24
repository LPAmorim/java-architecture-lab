package school.sptech.se_vira.dto;

import school.sptech.se_vira.model.TaskStatus;

public class StatusCountDto {

    private TaskStatus status;
    private long count;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
