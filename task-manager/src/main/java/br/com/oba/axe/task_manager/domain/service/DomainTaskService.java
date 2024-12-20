package br.com.oba.axe.task_manager.domain.service;

import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        //TODO implement validation
        return repository.save(task);
    }

    @Override
    @Logifier
    public void delete(Long id) {
        //TODO implement validation
        repository.deleteById(id);
    }

    @Override
    @Logifier
    public Task findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Logifier
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    @Logifier
    public List<Task> findByStatus(String status) {
        return repository.findByStatus(status).orElse(List.of());
    }

    @Override
    @Logifier
    public List<Task> findByCreationDate(LocalDateTime start, LocalDateTime end) {
        return repository.findByCreationDateBetween(start, end).orElse(List.of());
    }

    @Override
    @Logifier
    public List<Task> findByExecutionDate(LocalDateTime start, LocalDateTime end) {
        return repository.findByExecutionDateBetween(start, end).orElse(List.of());
    }
}
