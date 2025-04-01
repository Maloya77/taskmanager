/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.controller;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import com.sibusiso.taskmanager.taskmanager.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

     // Constructor-based Dependency Injection
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
     @Autowired
    private TaskService taskService;

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
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    // Create a task
 @PostMapping  //  Handles POST requests at /tasks
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }



    // Update a task
   @PutMapping("/{id}")
public Task updateTask(@PathVariable("id") Long id, @RequestBody Task taskDetails) {
    return taskService.updateTask(id, taskDetails);
}

    // Delete a task
   @DeleteMapping("/{id}")
public void deleteTask(@PathVariable("id") Long id) {
    taskService.deleteTask(id);
}
}
