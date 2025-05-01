/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import com.sibusiso.taskmanager.taskmanager.exception.UsernameNotFoundException;
import com.sibusiso.taskmanager.taskmanager.model.Priority;
import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.TaskStatus;
import com.sibusiso.taskmanager.taskmanager.model.User;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import com.sibusiso.taskmanager.taskmanager.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramph
 */

@Service
public class TaskService {
    
        private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
   

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
        }

    // Get task by ID
  public Task findById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional.orElse(null); // Return task if found, else return null
    }
    // Get tasks by status
   public List<Task> getTasksByStatus(String statusString) {
    TaskStatus status = TaskStatus.valueOf(statusString.toUpperCase()); 
    return taskRepository.findByStatus(status);
}


    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Update an existing task
   public Task updateTask(Long id, Task taskDetails) {
    return taskRepository.findById(id)
        .map(task -> {
            if (Boolean.TRUE.equals(task.getIsLocked())) {
                throw new IllegalStateException("Task is locked and cannot be updated.");
            }

            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setPriority(taskDetails.getPriority());
            task.setDueDate(taskDetails.getDueDate());
            task.setAssignedTo(taskDetails.getAssignedTo());
            task.setAttachments(taskDetails.getAttachments());

            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
}


    // Delete a task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

   public List<Task> getTasksDueOnDate(LocalDateTime start, LocalDateTime end) {
    return taskRepository.findByDueDateBetween(start, end);
}


     public List<Task> getTasksAssignedTo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return taskRepository.findByAssignedTo(user);
    }

    public List<Task> getTasksAssignedBy(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return taskRepository.findByAssignedBy(user);
    }

public boolean isTaskLocked(Task task) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime dueDate = task.getDueDate();

    if (dueDate == null) return false;

    long hoursUntilDue = Duration.between(now, dueDate).toHours();

    // Lock if due within 24 hours
    if (hoursUntilDue <= 24) {
        return true;
    }

    // Optional: Lock HIGH priority tasks due in 3 days
    if (task.getPriority() == Priority.HIGH) {
    long daysUntilDue = Duration.between(now, dueDate).toDays();
    return daysUntilDue <= 3;
}


    return false;
}

}
