package school.sptech.se_vira.adapter.in.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import school.sptech.se_vira.adapter.in.web.dto.StatusCountDto;
import school.sptech.se_vira.adapter.in.web.dto.TaskResponseDto;
import school.sptech.se_vira.adapter.in.web.mapper.TaskMapper;
import school.sptech.se_vira.adapter.in.web.dto.TaskRequestDto;
import school.sptech.se_vira.domain.model.TaskStatus;
import school.sptech.se_vira.domain.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // GET /tasks
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> listAll() {
        List<TaskResponseDto> tasks = service.findAll();
        if (tasks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(tasks);
    }

    // GET /tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /tasks
    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@RequestBody @Valid TaskRequestDto dto) {
        TaskResponseDto created = service.create(TaskMapper.toEntity(dto));
        URI location = URI.create("/tasks/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    // PUT /tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid TaskRequestDto dto) {
        return ResponseEntity.ok(service.update(id, TaskMapper.toEntity(dto)));
    }

    // DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET /tasks/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDto>> findByStatus(@PathVariable TaskStatus status) {
        List<TaskResponseDto> result = service.findByStatus(status);
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }

    // GET /tasks/search?keyword=xyz
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDto>> searchByTitle(@RequestParam String keyword) {
        List<TaskResponseDto> result = service.findByTitleKeyword(keyword);
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }

    // GET /tasks/count-by-status
    @GetMapping("/count-by-status")
    public ResponseEntity<List<StatusCountDto>> countByStatus() {
        List<StatusCountDto> result = service.findByDueDateRange().entrySet().stream()
                .map(e -> new StatusCountDto(e.getKey(), e.getValue()))
                .toList();
        return ResponseEntity.ok(result);
    }
}
