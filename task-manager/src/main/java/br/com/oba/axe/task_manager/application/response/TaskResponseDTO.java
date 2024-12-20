package br.com.oba.axe.task_manager.application.response;

import br.com.oba.axe.task_manager.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime executionDate;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.creationDate = task.getCreationDate();
        this.executionDate = task.getExecutionDate();
    }
}
