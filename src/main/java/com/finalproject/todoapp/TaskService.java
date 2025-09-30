package com.finalproject.todoapp;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    // Public method to add a task
    public Task addTask(String name) {
        validateTaskName(name); // centralized validation
        Task t = new Task(name);
        return repo.save(t);
    }

    // Public method to get all tasks
    public List<Task> getAll() {
        return repo.findAll();
    }


    public Optional<Task> findById(Long id) {
        return repo.findById(id);
    }

    public boolean deleteTask(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    private void validateTaskName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidTaskException("Task name cannot be empty");
        }
    }
}
