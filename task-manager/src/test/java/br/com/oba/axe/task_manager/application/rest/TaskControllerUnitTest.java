package br.com.oba.axe.task_manager.application.rest;

import br.com.oba.axe.task_manager.application.request.TaskRequestDTO;
import br.com.oba.axe.task_manager.domain.Task;
import br.com.oba.axe.task_manager.domain.service.DomainTaskService;
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
import static org.mockito.Mockito.when;

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

        ResponseEntity<List<Task>> response = controller.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Task> tasks = response.getBody();
        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetAllEmpty() {
        when(service.findAll()).thenReturn(List.of());
        ResponseEntity<List<Task>> response = controller.findAll();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Task> tasks = response.getBody();
        assertEquals(0, tasks.size());
    }

    @Test
    void testGetAllNull() {
        when(service.findAll()).thenReturn(null);
        ResponseEntity<List<Task>> response = controller.findAll();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Task> tasks = response.getBody();
        assertEquals(0, tasks.size());
    }

    @Test
    void testGetById() {
        Task theTask = createTask();
        when(service.findById(theTask.getId())).thenReturn(theTask);
        ResponseEntity<Task> response = controller.findById(theTask.getId());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Task task = response.getBody();
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
    }

    @Test
    void testGetByIdNull() {
        when(service.findById(1L)).thenReturn(null);
        ResponseEntity<Task> response = controller.findById(1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Task task = response.getBody();
        assertNull(task);
    }

    @Test
    void testGetByIdNotFound() {
        when(service.findById(1L)).thenReturn(null);
        ResponseEntity<Task> response = controller.findById(1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Task task = response.getBody();
        assertNull(task);
    }

    @Test
    void testSave() {
        Task theTask = createTask();
        TaskRequestDTO taskRequestDTO = createTaskRequestDTO(theTask);
        when(service.save(any(Task.class))).thenReturn(theTask);
        ResponseEntity<Task> response = controller.save(taskRequestDTO);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Task task = response.getBody();
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
        ResponseEntity<Task> response = controller.update(1L, taskRequestDTO);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Task task = response.getBody();
        assertEquals(theTask.getTitle(), task.getTitle());
        assertEquals(theTask.getDescription(), task.getDescription());
        assertEquals(theTask.getStatus(), task.getStatus());
        assertEquals(theTask.getCreationDate(), task.getCreationDate());
        assertEquals(theTask.getExecutionDate(), task.getExecutionDate());
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