/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

/**
 *
 * @author ramph
 */
@Entity
@Table(name = "tasks") // Table name in MySQL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "custom_reminder_time")
    private LocalDateTime customReminderTime;

    @Column(name = "last_reminder_sent")
    private LocalDateTime lastReminderSent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @ElementCollection
private List<String> attachments;

    
    public Boolean getIsLocked() {
        return isLocked;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(nullable = false)
    private LocalDateTime dueDate; // Updated to LocalDateTime

    @Column(name = "is_locked", columnDefinition = "TINYINT(1)")
    private Boolean isLocked = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp when task was created

    @Column(nullable = false)
    private LocalDateTime updatedAt; // Timestamp for last update

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    @JsonBackReference(value = "assignedTo-task") // To break the cycle on the Task side
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    @JsonBackReference(value = "createdBy-task")
    private User assignedBy;

   

    // Auto-set createdAt and updatedAt timestamps before persisting/updating
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

   

    public LocalDateTime getCustomReminderTime() {
        return customReminderTime;
    }

    public void setCustomReminderTime(LocalDateTime customReminderTime) {
        this.customReminderTime = customReminderTime;
    }

    public LocalDateTime getLastReminderSent() {
        return lastReminderSent;
    }

    public void setLastReminderSent(LocalDateTime lastReminderSent) {
        this.lastReminderSent = lastReminderSent;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

}
