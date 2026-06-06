/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author ramph
 */
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    @Transactional
    public void testUpdateFailsWhenTaskIsLocked() {
        // Setup: create and lock a task
    final    Task task = new Task();
    task.setId(1L);
        task.setTitle("Critical Task");
        task.setDescription("Do not edit");
        task.setIsLocked(true);
        Task savedTask = taskRepository.save(task);

        // Attempt update
        Task updateAttempt = new Task();
        updateAttempt.setTitle("Edited Title");

        Long taskId=savedTask.getId();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            taskService.updateTask(taskId, updateAttempt);
        });

        assertEquals("Task is locked and cannot be updated.", exception.getMessage());
    }
}

