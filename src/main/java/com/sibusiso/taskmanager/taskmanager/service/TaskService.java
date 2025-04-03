/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.User;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import java.time.LocalDate;
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
    
       @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
    public List<Task> getTasksByStatus(String status) {
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
    
    public List<Task> getTasksByPriority(Integer priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksWithinDueDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findTasksWithinDueDateRange(startDate, endDate);
    }

   public List<Task> getTasksAssignedTo(User user) {
        return taskRepository.findByAssignedTo(user);
    }

    public List<Task> getTasksAssignedBy(User user) {
        return taskRepository.findByAssignedBy(user);
    }
}
