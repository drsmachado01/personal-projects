package br.com.oba.axe.task_manager.domain.repository;

import br.com.oba.axe.task_manager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findByExecutionDateBetween(LocalDateTime start, LocalDateTime end);

    Optional<List<Task>> findByStatus(String status);

    Optional<List<Task>> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);
}
