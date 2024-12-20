package br.com.oba.axe.task_manager.domain.service;

import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.repository.TaskRepository;
import br.com.oba.axe.task_manager.domain.service.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DomainTaskService implements TaskService {
    @Autowired
    private TaskRepository repository;

    @Override
    @Logifier
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    @Logifier
    public Task update(Long id, Task task) {
        var existingTask = repository.findById(id).orElseThrow(() -> new DomainException("Task not found"));
        Objects.requireNonNull(existingTask);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setCreationDate(task.getCreationDate());
        existingTask.setExecutionDate(task.getExecutionDate());
        return repository.save(existingTask);
    }

    @Override
    @Logifier
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new DomainException("Task not found"));
        repository.deleteById(id);
    }

    @Override
    @Logifier
    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DomainException("Task not found"));
    }

    @Override
    @Logifier
    public List<Task> findAll() {
        var tasks = repository.findAll();
        if(tasks.isEmpty()) {
            throw new DomainException("No tasks found");
        } else {
            return tasks;
        }
    }

    @Override
    @Logifier
    public List<Task> findByStatus(String status) {
        return repository.findByStatus(status).orElseThrow(() -> new DomainException("No tasks found"));
    }

    @Override
    @Logifier
    public List<Task> findByCreationDate(LocalDateTime start, LocalDateTime end) {
        return repository.findByCreationDateBetween(start, end).orElseThrow(() -> new DomainException("No tasks found"));
    }

    @Override
    @Logifier
    public List<Task> findByExecutionDate(LocalDateTime start, LocalDateTime end) {
        return repository.findByExecutionDateBetween(start, end).orElseThrow(() -> new DomainException("No tasks found"));
    }
}
