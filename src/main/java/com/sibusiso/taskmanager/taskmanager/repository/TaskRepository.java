/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.repository;

import com.sibusiso.taskmanager.taskmanager.model.Priority;
import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.TaskStatus;
import com.sibusiso.taskmanager.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ramph
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    
    
        // Find tasks by Status
        List<Task> findByStatus(TaskStatus status); // Example: Find tasks by status

        // Find tasks by priority
    List<Task> findByPriority(Priority priority);

    // Find tasks within a due date 
   List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);


    // Find tasks by assigned By
    List<Task> findByAssignedBy(User assignedBy);
    
        // Find tasks by assigned To
    List<Task> findByAssignedTo(User assignedTo);
}
