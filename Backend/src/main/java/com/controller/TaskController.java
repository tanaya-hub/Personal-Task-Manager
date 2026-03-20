package com.controller;

import com.model.Task;

import com.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication authentication) {
        String username = authentication.getName(); // 🔥 from JWT
        return taskService.createTask(task, username);
    }
    

    @GetMapping
    public List<Task> getTasks(Authentication authentication) {
        String username = authentication.getName(); // 🔥 from JWT
        return taskService.getTasksByUsername(username);
    }
    
    @GetMapping("/board/{boardId}")
    public List<Task> getTasksByBoard(@PathVariable Long boardId) {
        return taskService.getTasksByBoard(boardId);
    }
    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTaskStatus(id, task.getStatus());
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
    @PutMapping("/{taskId}/assign/{userId}")
    public Task assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.assignTaskToUser(taskId, userId);
    }
    
}