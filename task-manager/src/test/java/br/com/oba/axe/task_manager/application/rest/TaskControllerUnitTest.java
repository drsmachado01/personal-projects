package br.com.oba.axe.task_manager.application.rest;

import br.com.oba.axe.task_manager.application.request.TaskRequestDTO;
import br.com.oba.axe.task_manager.application.response.TaskResponseDTO;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.service.DomainTaskService;
import br.com.oba.axe.task_manager.domain.service.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerUnitTest {
    @Mock
    private DomainTaskService service;

    @InjectMocks
    private TaskController controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Task> theList = createListOfTasks();
        Task theTask = theList.get(0);
        when(service.findAll()).thenReturn(theList);

        ResponseEntity<List<TaskResponseDTO>> response = controller.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TaskResponseDTO> tasks = response.getBody();
        assertEquals(1, tasks.size());
        TaskResponseDTO task = tasks.get(0);
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetAllEmpty() {
        when(service.findAll()).thenThrow(new DomainException("No tasks found"));
        try {
            controller.findAll();
        } catch (DomainException e) {
            assertEquals("No tasks found", e.getMessage());
        }
    }

    @Test
    void testGetByStatus() {
        List<Task> theList = createListOfTasks();
        Task theTask = theList.get(0);
        when(service.findByStatus("CREATED")).thenReturn(theList);

        ResponseEntity<List<TaskResponseDTO>> response = controller.findByStatus("CREATED");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TaskResponseDTO> tasks = response.getBody();
        assertEquals(1, tasks.size());
        TaskResponseDTO task = tasks.get(0);
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetByStatusEmpty() {
        when(service.findByStatus("CREATED")).thenThrow(new DomainException("No tasks found"));
        try {
            controller.findByStatus("CREATED");
        } catch (DomainException e) {
            assertEquals("No tasks found", e.getMessage());
        }
    }

    @Test
    void testGetByCreationDate() {
    	LocalDateTime start = LocalDateTime.now();
    	LocalDateTime end = start.plusDays(1L);
        List<Task> theList = createListOfTasks();
        Task theTask = theList.get(0);
        when(service.findByCreationDate(start, end)).thenReturn(theList);

        ResponseEntity<List<TaskResponseDTO>> response = controller.findByCreationDate(start, end);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TaskResponseDTO> tasks = response.getBody();
        assertEquals(1, tasks.size());
        TaskResponseDTO task = tasks.get(0);
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetByCreationDateEmpty() {
    	LocalDateTime start = LocalDateTime.now();
    	LocalDateTime end = start.plusDays(1L);
    	
        when(service.findByCreationDate(start, end)).thenThrow(new DomainException("No tasks found"));
        try {
        	controller.findByCreationDate(start, end);
        } catch (DomainException e) {
            assertEquals("No tasks found", e.getMessage());
        }
    }

    @Test
    void testGetByExecutionnDate() {
    	LocalDateTime start = LocalDateTime.now();
    	LocalDateTime end = start.plusDays(1L);
        List<Task> theList = createListOfTasks();
        Task theTask = theList.get(0);
        when(service.findByExecutionDate(start, end)).thenReturn(theList);

        ResponseEntity<List<TaskResponseDTO>> response = controller.findByExecutionDate(start, end);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<TaskResponseDTO> tasks = response.getBody();
        assertEquals(1, tasks.size());
        TaskResponseDTO task = tasks.get(0);
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetByExecutionnDateEmpty() {
    	LocalDateTime start = LocalDateTime.now();
    	LocalDateTime end = start.plusDays(1L);
    	
        when(service.findByExecutionDate(start, end)).thenThrow(new DomainException("No tasks found"));
        try {
        	controller.findByExecutionDate(start, end);
        } catch (DomainException e) {
            assertEquals("No tasks found", e.getMessage());
        }
    }

    @Test
    void testGetAllNull() {
        when(service.findAll()).thenThrow(new DomainException("No tasks found"));
        try {
            controller.findAll();
        } catch (DomainException e) {
            assertEquals("No tasks found", e.getMessage());
        }
    }

    @Test
    void testGetById() {
        Task theTask = createTask();
        when(service.findById(theTask.getId())).thenReturn(theTask);
        ResponseEntity<TaskResponseDTO> response = controller.findById(theTask.getId());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        TaskResponseDTO task = response.getBody();
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetByIdNull() {
        when(service.findById(1L)).thenThrow(new DomainException("Task not found"));
        try {
            controller.findById(1L);
        } catch (DomainException e) {
            assertEquals("Task not found", e.getMessage());
        }
    }

    @Test
    void testGetByIdNotFound() {
        when(service.findById(1L)).thenThrow(new DomainException("Task not found"));
        try {
            controller.findById(1L);
        } catch (DomainException e) {
            assertEquals("Task not found", e.getMessage());
        }
    }

    @Test
    void testSave() {
        Task theTask = createTask();
        TaskRequestDTO taskRequestDTO = createTaskRequestDTO(theTask);
        when(service.save(any(Task.class))).thenReturn(theTask);
        ResponseEntity<TaskResponseDTO> response = controller.save(taskRequestDTO);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        TaskResponseDTO task = response.getBody();
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testUpdate() {
        Task theTask = createTask();
        TaskRequestDTO taskRequestDTO = createTaskRequestDTO(theTask);
        when(service.update(any(Long.class), any(Task.class))).thenReturn(theTask);
        ResponseEntity<TaskResponseDTO> response = controller.update(1L, taskRequestDTO);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        TaskResponseDTO task = response.getBody();
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testUpdateNotFound() {
        TaskRequestDTO taskRequestDTO = createTaskRequestDTO(createTask());
        when(service.update(any(Long.class), any(Task.class))).thenThrow(new DomainException("Task not found"));
        try {
            controller.update(1L, taskRequestDTO);
        } catch (DomainException e) {
            assertEquals("Task not found", e.getMessage());
        }
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete(any(Long.class));
        ResponseEntity<Void> response = controller.delete(1L);
        verify(service).delete(any(Long.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteNotFound() {
        doThrow(new DomainException("Task not found")).when(service).delete(any(Long.class));
        try {
            controller.delete(1L);
        } catch (DomainException e) {
            assertEquals("Task not found", e.getMessage());
        }
    }

    private List<Task> createListOfTasks() {
        return List.of(createTask());
    }

    private Task createTask() {
        return Task.builder()
                .creationDate(LocalDateTime.now())
                .title("TODO")
                .description("That is a todo task")
                .executionDate(LocalDateTime.now().plusDays(1L))
                .status("CREATED")
                .build();
    }

    private TaskRequestDTO createTaskRequestDTO(Task task) {
        return TaskRequestDTO.builder()
                .id(task.getId())
                .creationDate(task.getCreationDate())
                .title(task.getTitle())
                .description(task.getDescription())
                .executionDate(task.getExecutionDate())
                .status(task.getStatus())
                .build();
    }
}