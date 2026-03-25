package school.sptech.se_vira.service;

import school.sptech.se_vira.exception.DuplicateTitleException;
import school.sptech.se_vira.exception.TaskNotFoundException;
import school.sptech.se_vira.model.Task;
import school.sptech.se_vira.model.TaskStatus;
import school.sptech.se_vira.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(Integer id) {
        return repository.findById(id)
                         .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(Task task) {
        repository.findByTitleIgnoreCase(task.getTitle())
                  .ifPresent(t ->  { throw new DuplicateTitleException(task.getTitle()); });

        return repository.save(task);
    }

    public Task update(Integer id, Task task) {
        Task existing = repository.findById(id)
                  .orElseThrow(() -> new TaskNotFoundException(id));

        repository.findByTitleIgnoreCase(task.getTitle())
                  .ifPresent(t ->  { throw new DuplicateTitleException(task.getTitle()); });

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setDueDate(task.getDueDate());
        existing.setStatus(task.getStatus());

        return repository.save(existing);
    }

    public void deleteById(Integer id) {
        repository.findById(id)
                  .orElseThrow(() -> new TaskNotFoundException(id));

        repository.deleteById(id);
    }

    public List<Task> findByStatus(TaskStatus status) {
        return repository.findByStatus(status);
        }

    public List<Task> findByTitleKeyword(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }

    public Map<TaskStatus, Long> countByAllStatuses() {
        return Arrays.stream(TaskStatus.values())
                     .collect(Collectors.toMap(s -> s, repository::countByStatus));
    }
}
