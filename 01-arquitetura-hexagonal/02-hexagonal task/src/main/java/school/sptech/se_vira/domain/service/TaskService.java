package school.sptech.se_vira.domain.service;

import school.sptech.se_vira.adapter.in.web.dto.TaskResponseDto;
import school.sptech.se_vira.adapter.in.web.mapper.TaskMapper;
import school.sptech.se_vira.domain.exception.DuplicateTitleException;
import school.sptech.se_vira.domain.exception.TaskNotFoundException;
import school.sptech.se_vira.domain.model.Task;
import school.sptech.se_vira.domain.model.TaskStatus;
import school.sptech.se_vira.domain.port.in.TaskUseCase;
import school.sptech.se_vira.domain.port.out.TaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskUseCase {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskResponseDto> findAll() {
        return TaskMapper.toResponseDtoList(repository.findAll());
    }

    @Override
    public TaskResponseDto findById(Integer id) {
        return TaskMapper.toResponseDto(repository.findById(id)
                                               .orElseThrow(() -> new TaskNotFoundException(id)));      
    }
    
    @Override
    public TaskResponseDto create(Task task) {
        repository.findByTitleIgnoreCase(task.getTitle())
                  .ifPresent(t -> { throw new DuplicateTitleException(task.getTitle()); });

        return TaskMapper.toResponseDto(repository.save(task));
    }

    @Override
    public TaskResponseDto update(Integer id, Task task) {
        findById(id);

        if (repository.existsByTitleIgnoreCaseAndIdNot(task.getTitle(), id)) {
            throw new DuplicateTitleException(task.getTitle());
        }

        task.setId(id);

        return TaskMapper.toResponseDto(repository.save(task));
    }

    @Override
    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public List<TaskResponseDto> findByStatus(TaskStatus status) {
        return TaskMapper.toResponseDtoList(repository.findByStatus(status));
    }

    @Override
    public List<TaskResponseDto> findByTitleKeyword(String keyword) {
        return TaskMapper.toResponseDtoList(repository.findByTitleLikeIgnoreCase(keyword));
    }

    @Override
    public Map<TaskStatus, Long> findByDueDateRange() {
        return Arrays.stream(TaskStatus.values())
                     .collect(Collectors.toMap(s -> s, repository::countByStatus));
    }

    
}
