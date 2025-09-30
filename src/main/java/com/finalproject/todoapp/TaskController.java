package com.finalproject.todoapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // Add task via JSON body
    @PostMapping
    public ResponseEntity<Task> add(@RequestBody Task req) {
        Task saved = service.addTask(req.getName());
        return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId())).body(saved);
    }

    // Add task via form submission (e.g., from HTML)
    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestParam String name) {
        Task saved = service.addTask(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // List all tasks
    @GetMapping
    public List<Task> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return service.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.status(404).body(
                        (Task) Collections.singletonMap("error", "Task with id " + id + " not found")
                ));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = service.deleteTask(id);
            if (deleted) {
                return ResponseEntity.noContent().build(); // 204
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Task with id " + id + " not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Internal server error: " + e.getMessage()));
        }
    }


}