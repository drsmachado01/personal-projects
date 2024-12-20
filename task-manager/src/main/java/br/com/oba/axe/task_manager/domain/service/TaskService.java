package br.com.oba.axe.task_manager.domain.service;

import br.com.oba.axe.task_manager.domain.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    Task save(Task task);

    Task update(Long id, Task task);

    void delete(Long id);

    Task findById(Long id);

    List<Task> findAll();

    List<Task> findByStatus(String status);

    List<Task> findByCreationDate(LocalDateTime start, LocalDateTime end);

    List<Task> findByExecutionDate(LocalDateTime start, LocalDateTime end);
}
