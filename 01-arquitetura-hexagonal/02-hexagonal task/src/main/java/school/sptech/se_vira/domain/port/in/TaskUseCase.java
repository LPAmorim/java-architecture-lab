package school.sptech.se_vira.domain.port.in;

import java.util.List;
import java.util.Map;

import school.sptech.se_vira.adapter.in.web.dto.TaskResponseDto;
import school.sptech.se_vira.domain.model.Task;
import school.sptech.se_vira.domain.model.TaskStatus;

public interface TaskUseCase {
    
    List<TaskResponseDto> findAll();

    TaskResponseDto findById(Integer id);

    TaskResponseDto create(Task task);

    TaskResponseDto update(Integer id, Task task);
    
    void deleteById(Integer id);
    
    List<TaskResponseDto> findByStatus(TaskStatus status);
    
    List<TaskResponseDto> findByTitleKeyword(String keyword);

    Map<TaskStatus, Long> findByDueDateRange();
}
