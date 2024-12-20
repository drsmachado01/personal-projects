package br.com.oba.axe.task_manager.application.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.oba.axe.task_manager.application.request.TaskRequestDTO;
import br.com.oba.axe.task_manager.application.response.TaskResponseDTO;
import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
    public ResponseEntity<TaskResponseDTO> save(@RequestBody TaskRequestDTO request) {
        var taskResponse = new TaskResponseDTO(service.save(
                Task.builder()
                        .id(request.getId())
                        .creationDate(request.getCreationDate())
                        .description(request.getDescription())
                        .executionDate(request.getExecutionDate())
                        .status(request.getStatus())
                        .title(request.getTitle())
                        .build()
        ));
        addSelfLink(taskResponse.getId(), taskResponse);
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/")
    @Logifier
    public ResponseEntity<CollectionModel<TaskResponseDTO>> findAll() {
        var tasks = service.findAll().stream().map(TaskResponseDTO::new).toList();
        
        tasks.forEach(t -> addSelfLink(t.getId(), t));
        
        var link = linkTo(TaskController.class).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(tasks, link));
    }

    @GetMapping("/{id}")
    @Logifier
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable("id") Long id) {
        var task = new TaskResponseDTO(service.findById(id));
        addSelfLink(id, task);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/status/{status}")
    @Logifier
    public ResponseEntity<List<TaskResponseDTO>> findByStatus(@PathVariable String status) {
        var tasks = service.findByStatus(status).stream().map(TaskResponseDTO::new).toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/creationDate/{start}/{end}")
    @Logifier
    public ResponseEntity<List<TaskResponseDTO>> findByCreationDate(@PathVariable LocalDateTime start, @PathVariable LocalDateTime end) {
        var tasks = service.findByCreationDate(start, end).stream().map(TaskResponseDTO::new).toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/executionDate/{start}/{end}")
    @Logifier
    public ResponseEntity<List<TaskResponseDTO>> findByExecutionDate(@PathVariable LocalDateTime start, @PathVariable LocalDateTime end) {
        var tasks = service.findByExecutionDate(start, end).stream().map(TaskResponseDTO::new).toList();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    @Logifier
    public ResponseEntity<TaskResponseDTO> update(@PathVariable("id") Long id, @RequestBody TaskRequestDTO request) {
        return ResponseEntity.ok(new TaskResponseDTO(service.update(id, Task.builder()
                .id(request.getId())
                .creationDate(request.getCreationDate())
                .description(request.getDescription())
                .executionDate(request.getExecutionDate())
                .status(request.getStatus())
                .title(request.getTitle())
                .build())));
    }

    @DeleteMapping("/{id}")
    @Logifier
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    private void addSelfLink(Long id, TaskResponseDTO dto) {
    	dto.add(linkTo(methodOn(TaskController.class).findById(id)).withSelfRel());
    }
    
}
