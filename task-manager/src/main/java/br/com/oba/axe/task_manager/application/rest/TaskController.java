package br.com.oba.axe.task_manager.application.rest;

import br.com.oba.axe.task_manager.application.request.TaskRequestDTO;
import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;

    @Autowired
    @Logifier
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/")
    @Logifier
    public ResponseEntity<Task> save(@RequestBody TaskRequestDTO request) {
        return ResponseEntity.ok(service.save(
                Task.builder()
                        .id(request.getId())
                        .creationDate(request.getCreationDate())
                        .description(request.getDescription())
                        .executionDate(request.getExecutionDate())
                        .status(request.getStatus())
                        .title(request.getTitle())
                        .build()
        ));
    }

    @GetMapping("/")
    @Logifier
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Logifier
    public ResponseEntity<Task> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/status/{status}")
    @Logifier
    public ResponseEntity<List<Task>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/creationDate/{start}/{end}")
    @Logifier
    public ResponseEntity<List<Task>> findByCreationDate(@PathVariable LocalDateTime start, @PathVariable LocalDateTime end) {
        return ResponseEntity.ok(service.findByCreationDate(start, end));
    }

    @GetMapping("/executionDate/{start}/{end}")
    @Logifier
    public ResponseEntity<List<Task>> findByExecutionDate(@PathVariable LocalDateTime start, @PathVariable LocalDateTime end) {
        return ResponseEntity.ok(service.findByExecutionDate(start, end));
    }

    @PutMapping("/{id}")
    @Logifier
    public ResponseEntity<Task> update(@PathVariable("id") Long id, @RequestBody TaskRequestDTO request) {
        return ResponseEntity.ok(service.update(id, Task.builder()
                .id(request.getId())
                .creationDate(request.getCreationDate())
                .description(request.getDescription())
                .executionDate(request.getExecutionDate())
                .status(request.getStatus())
                .title(request.getTitle())
                .build()));
    }

    @DeleteMapping("/{id}")
    @Logifier
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
