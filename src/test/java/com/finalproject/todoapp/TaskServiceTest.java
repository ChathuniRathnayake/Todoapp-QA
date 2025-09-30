package com.finalproject.todoapp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class TaskServiceTest {

    @Test
    void addTask_withValidName_shouldSaveAndReturn() {
        TaskRepository repo = Mockito.mock(TaskRepository.class);
        Mockito.when(repo.save(Mockito.any(Task.class)))
                .thenAnswer(i -> {
                    Task t = i.getArgument(0);
                    // simulate DB id assignment
                    return t;
                });
        TaskService service = new TaskService(repo);

        Task result = service.addTask("Buy milk");
        assertNotNull(result);
        assertEquals("Buy milk", result.getName());
        Mockito.verify(repo).save(Mockito.any(Task.class));
    }

    @Test
    void addTask_withEmptyName_shouldThrow() {
        TaskRepository repo = Mockito.mock(TaskRepository.class);
        TaskService service = new TaskService(repo);
        assertThrows(InvalidTaskException.class, () -> service.addTask(""));
    }
}
