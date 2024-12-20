package br.com.oba.axe.task_manager.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.repository.TaskRepository;
import br.com.oba.axe.task_manager.domain.service.exception.DomainException;

public class DomainTaskServiceUnitTest {
	@Mock
	private TaskRepository repository;

	@InjectMocks
	private DomainTaskService service;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveATask() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		task.setId(null);
		when(repository.save(any(Task.class))).thenReturn(task);

		var result = service.save(task);
		task.setId(1L);
		assertNotNull(result);
		assertEquals(task, result);
	}

	@Test
	void testGetAllTasks() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		var tasks = List.of(task);
		when(repository.findAll()).thenReturn(tasks);

		var result = service.findAll();
		assertNotNull(result);
		assertEquals(1, result.size());
		Task theTask = result.get(0);
		assertNotNull(theTask);
		assertEquals(task, theTask);
	}

	@Test
    void testGetAllTasksNull() {
    	when(repository.findAll()).thenReturn(List.of());
    	
    	try {
    		service.findAll();
    	} catch(DomainException ex) {
    		assertNotNull(ex);
    		assertEquals("No tasks found", ex.getMessage());
    	}
    }

	@Test
	void testGetByID() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);

		when(repository.findById(1L)).thenReturn(Optional.of(task));
		var result = service.findById(1L);
		assertNotNull(result);
		assertEquals(task, result);
	}

	@Test
    void testGetByIdNotFound() {    	
		when(repository.findById(anyLong())).thenReturn(Optional.empty());
		try {
			service.findById(1L);
		} catch (DomainException ex) {
			assertNotNull(ex);
			assertEquals("Task not found", ex.getMessage());
		}
    }

	@Test
	void testGetByStatus() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		var tasks = List.of(task);

		when(repository.findByStatus("CREATED")).thenReturn(Optional.of(tasks));
		var result = service.findByStatus("CREATED");
		assertNotNull(result);
		assertEquals(1, result.size());
		var aTask = tasks.get(0);
		assertNotNull(aTask);
		assertEquals(task, aTask);
	}

	@Test
    void testGetByStatusNoTasks() {		
		when(repository.findByStatus("CREATED")).thenReturn(Optional.empty());
		try {
			service.findByStatus("CREATED");
		} catch(DomainException ex) {
			assertNotNull(ex);
			assertEquals("No tasks found", ex.getMessage());
		}
    }

	@Test
	void testGetByCreationDate() {
		String sStartDate = "2024-12-15T10:00:00";
		String sEndDate = "2024-12-20T10:00:00";

		var startDate = stringToLDT(sStartDate);
		var endDate = stringToLDT(sEndDate);

		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		var tasks = List.of(task);

		when(repository.findByCreationDateBetween(startDate, endDate)).thenReturn(Optional.of(tasks));

		var result = service.findByCreationDate(startDate, endDate);
		assertNotNull(result);
		assertEquals(1, result.size());
		var aTask = tasks.get(0);
		assertNotNull(aTask);
		assertEquals(task, aTask);
	}

	@Test
	void testGetByCreationDateNoTasks() {
		String sStartDate = "2024-12-15T10:00:00";
		String sEndDate = "2024-12-20T10:00:00";

		var startDate = stringToLDT(sStartDate);
		var endDate = stringToLDT(sEndDate);

		when(repository.findByCreationDateBetween(startDate, endDate)).thenReturn(Optional.empty());

		try {
			service.findByCreationDate(startDate, endDate);
		} catch (DomainException ex) {
			assertNotNull(ex);
			assertEquals("No tasks found", ex.getMessage());
		}
	}

	@Test
	void testGetByExecutionDate() {
		String sStartDate = "2024-12-15T10:00:00";
		String sEndDate = "2024-12-20T10:00:00";

		var startDate = stringToLDT(sStartDate);
		var endDate = stringToLDT(sEndDate);

		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		var tasks = List.of(task);

		when(repository.findByExecutionDateBetween(startDate, endDate)).thenReturn(Optional.of(tasks));

		var result = service.findByExecutionDate(startDate, endDate);
		assertNotNull(result);
		assertEquals(1, result.size());
		var aTask = tasks.get(0);
		assertNotNull(aTask);
		assertEquals(task, aTask);
	}

	@Test
	void testGetByExecutionDateNoTasks() {
		String sStartDate = "2024-12-15T10:00:00";
		String sEndDate = "2024-12-20T10:00:00";

		var startDate = stringToLDT(sStartDate);
		var endDate = stringToLDT(sEndDate);

		when(repository.findByExecutionDateBetween(startDate, endDate)).thenReturn(Optional.empty());

		try {
			service.findByExecutionDate(startDate, endDate);
		} catch (DomainException ex) {
			assertNotNull(ex);
			assertEquals("No tasks found", ex.getMessage());
		}
	}
	
	@Test
	void testUpdateTask() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		
		var updatedTask = createTask(null, now, after);
		updatedTask.setTitle("This is a new task");
		
		when(repository.findById(1L)).thenReturn(Optional.of(task));
		when(repository.save(task)).thenReturn(updatedTask);
		
		var result = service.update(1L, updatedTask);
		assertNotNull(result);
		assertEquals(updatedTask, result);
	}
	
	@Test
	void testUpdateTask_TaskNotFound() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		
		var updatedTask = createTask(null, now, after);
		updatedTask.setTitle("This is a new task");
		
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		try {
			service.update(1L, updatedTask);
		} catch (DomainException ex) {
			assertNotNull(ex);
			assertEquals("Task not found", ex.getMessage());
		}
	}
	
	@Test
	void testDelete() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		
		when(repository.findById(1L)).thenReturn(Optional.of(task));
		
		service.delete(1L);
		
		verify(repository, times(1)).findById(1L);
		verify(repository, times(1)).delete(task);
	}
	
	@Test
	void testDeleteTaskNotFound() {
		var now = LocalDateTime.now();
		var after = now.plusDays(1L);
		var task = createTask(1L, now, after);
		
		when(repository.findById(1L)).thenReturn(Optional.of(task));
		
		service.delete(1L);
		
		verify(repository, times(1)).findById(1L);
		verify(repository, times(1)).delete(task);
	}

	private LocalDateTime stringToLDT(String theDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return LocalDateTime.parse(theDate, formatter);
	}

	private Task createTask(Long id, LocalDateTime now, LocalDateTime after) {
		return Task.builder().id(id).creationDate(now).executionDate(after).title("A Task")
				.description("This is a task").status("CREATED").build();
	}
}
