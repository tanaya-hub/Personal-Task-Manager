package com.service;

import com.model.Task;
import com.model.TaskStatus;
import com.model.User;
import com.repository.TaskRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAssignedUser(user); // 🔥 VERY IMPORTANT

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getTasksByBoard(Long boardId) {
        return taskRepository.findByBoardId(boardId);
    }
    public Task updateTaskStatus(Long taskId, TaskStatus status) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);

        return taskRepository.save(task);
    }
    public String deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return "Task not found";
        }

        taskRepository.deleteById(id);
        return "Task deleted successfully";
    }
    @Autowired
    private UserRepository userRepository;

    public Task assignTaskToUser(Long taskId, Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAssignedUser(user);

        return taskRepository.save(task);
    }
    public List<Task> getTasksByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByAssignedUserId(user.getId());
    }
    
}