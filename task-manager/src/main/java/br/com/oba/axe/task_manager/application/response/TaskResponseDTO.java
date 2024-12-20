package br.com.oba.axe.task_manager.application.response;

import br.com.oba.axe.task_manager.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

@Getter
@ToString
@EqualsAndHashCode
public class TaskResponseDTO extends RepresentationModel<TaskResponseDTO> {
    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final LocalDateTime creationDate;
    private final LocalDateTime executionDate;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.creationDate = task.getCreationDate();
        this.executionDate = task.getExecutionDate();
    }
}
