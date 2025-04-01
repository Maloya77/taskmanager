/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.repository;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ramph
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    
        List<Task> findByStatus(String status); // Example: Find tasks by status

}
