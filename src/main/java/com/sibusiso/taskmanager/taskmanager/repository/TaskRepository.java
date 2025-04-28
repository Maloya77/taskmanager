/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.repository;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.TaskStatus;
import com.sibusiso.taskmanager.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;

/**
 *
 * @author ramph
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    
        // Find tasks by Status
        List<Task> findByStatus(TaskStatus status); // Example: Find tasks by status

        // Find tasks by priority
    List<Task> findByPriority(Integer priority);

    // Find tasks within a due date range
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksWithinDueDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Find tasks by assigned By
    List<Task> findByAssignedBy(User assignedBy);
    
        // Find tasks by assigned To
    List<Task> findByAssignedTo(User assignedTo);
}
