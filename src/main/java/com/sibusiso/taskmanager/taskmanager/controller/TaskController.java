/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.controller;

import com.sibusiso.taskmanager.taskmanager.dto.TaskResponse;
import com.sibusiso.taskmanager.taskmanager.model.Priority;
import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import com.sibusiso.taskmanager.taskmanager.repository.UserRepository;
import com.sibusiso.taskmanager.taskmanager.service.FileStorageService;
import com.sibusiso.taskmanager.taskmanager.service.TaskService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
   
  
private FileStorageService fileStorageService;

// Constructor-based Dependency Injection
    public TaskController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService, FileStorageService fileStorageService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
        this.fileStorageService=fileStorageService;
    }

    // Get all tasks
   @GetMapping
public List<TaskResponse> getAllTasks() {
    return taskService.getAllTasks().stream()
        .map(TaskResponse::new)
        .toList();
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
    
  //attachment upload  
    @PostMapping("/{id}/attachments")
public ResponseEntity<String> uploadAttachment(@PathVariable Long id,
                                               @RequestParam("file") MultipartFile file) {
    Task task = taskService.findById(id); 
    
String filename = fileStorageService.storeFile(id, file);

    task.getAttachments().add(filename);
    taskRepository.save(task);

    return ResponseEntity.ok("File uploaded: " + filename);
}

//attachment download
@GetMapping("/{id}/attachments/{filename}")
public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id,
                                                   @PathVariable String filename) {
    Resource file = fileStorageService.loadFile(id, filename);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file);
}

//deleting an attachment
@DeleteMapping("/{id}/attachments/{filename}")
public ResponseEntity<String> deleteAttachment(@PathVariable Long id,
                                               @PathVariable String filename) throws IOException {
    Task task = taskService.findById(id);
    fileStorageService.deleteFile(id, filename);

    task.getAttachments().remove(filename);
    taskRepository.save(task);

    return ResponseEntity.ok("File deleted: " + filename);
}

}
