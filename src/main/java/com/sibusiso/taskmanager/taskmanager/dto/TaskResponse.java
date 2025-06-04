/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.dto;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author ramph
 */
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime dueDate;
    private boolean isLocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> attachments;

    private String assignedByName;
    private String assignedToName;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus().name();
        this.priority = task.getPriority() != null ? task.getPriority().name() : null;
        this.dueDate = task.getDueDate();
        this.isLocked = task.getIsLocked() != null ? task.getIsLocked() : false;
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.attachments = task.getAttachments();

        this.assignedByName = task.getAssignedBy() != null
            ? task.getAssignedBy().getFullName()
            : "Self-assigned";

        this.assignedToName = task.getAssignedTo() != null
            ? task.getAssignedTo().getFullName()
            : "Unassigned";
    }
}