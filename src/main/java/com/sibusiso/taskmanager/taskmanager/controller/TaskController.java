/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.controller;

import com.sibusiso.taskmanager.taskmanager.exception.UsernameNotFoundException;
import com.sibusiso.taskmanager.taskmanager.model.Priority;
import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.User;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import com.sibusiso.taskmanager.taskmanager.repository.UserRepository;
import com.sibusiso.taskmanager.taskmanager.service.TaskService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ramph
 */

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

// Constructor-based Dependency Injection
    public TaskController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {  // Explicit name
        Task task = taskService.findById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get tasks by status
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable("status") String status) {
        return taskService.getTasksByStatus(status);
    }

    // Create a task
    @PostMapping("/create")  //  Handles POST requests at /tasks
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
       Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // Update a task
   @PutMapping("/{id}")
public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
    try {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}


    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
    }
// Filter tasks by priority

    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable("priority") Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    // Filter tasks within a due date range
    @GetMapping("/due-date")
public List<Task> getTasksByDueDate(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
    return taskService.getTasksDueOnDate(startOfDay, endOfDay);
}

   // Example: /api/tasks/assigned-to?username=john
    @GetMapping("/assigned-to")
    public ResponseEntity<List<Task>> getTasksAssignedTo(@RequestParam("username") String username) {
        List<Task> tasks = taskService.getTasksAssignedTo(username);
        return ResponseEntity.ok(tasks);
    }

    // Example: /api/tasks/assigned-by?username=jane
    @GetMapping("/assigned-by")
    public ResponseEntity<List<Task>> getTasksAssignedBy(@RequestParam("username") String username) {
        List<Task> tasks = taskService.getTasksAssignedBy(username);
        return ResponseEntity.ok(tasks);
    }
}
