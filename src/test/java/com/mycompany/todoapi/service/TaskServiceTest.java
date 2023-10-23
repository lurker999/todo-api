package com.mycompany.todoapi.service;

import com.mycompany.todoapi.dto.TaskDto;
import com.mycompany.todoapi.exception.BusinessException;
import com.mycompany.todoapi.exception.ErrorCode;
import com.mycompany.todoapi.model.Task;
import com.mycompany.todoapi.repository.TaskRepository;
import com.mycompany.todoapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
public class TaskServiceTest {

    public static final long CREATED_AT = Instant.now().toEpochMilli();

    @Autowired
    private TaskRepository taskRepository;
    @Mock
    private Supplier<Long> instantSupplier;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        when(instantSupplier.get()).thenReturn(CREATED_AT);
        taskService = new TaskServiceImpl(taskRepository, instantSupplier);
    }

    @Test
    public void shouldUpdateTask() {
        Task task = new Task();
        task.setCreatedAt(CREATED_AT);
        task.setTitle("task title");
        task = taskRepository.save(task);

        taskService.updateTask(task.getId(), new TaskDto("new title"));
        Task updatedTask = taskRepository.findById(task.getId()).get();

        assertEquals("new title", updatedTask.getTitle());
        assertEquals(instantSupplier.get(), updatedTask.getUpdatedAt());
    }

    @Test
    public void updateTaskShouldThrowExceptionWhenTaskNotFound() {
        try {
            taskService.updateTask(10L, new TaskDto("new title"));
        } catch (BusinessException exception) {
            assertEquals(ErrorCode.RESOURCE_NOT_FOUND, exception.getCode());
        }
    }

    @Test
    public void testCreateTask() {
        String title = "title";
        Task task = taskService.creatTask(new TaskDto(title));
        assertNotNull(task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(instantSupplier.get(), task.getCreatedAt());
    }
}
