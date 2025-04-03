/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String status; // Example: "Pending", "In Progress", "Completed"

    @Column(nullable = false)
    private Integer priority; // Example: 1 = High, 2 = Medium, 3 = Low

    @Column(nullable = false)
    private LocalDateTime dueDate; // Updated to LocalDateTime

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp when task was created

    @Column(nullable = false)
    private LocalDateTime updatedAt; // Timestamp for last update

     @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    @JsonBackReference // To break the cycle on the Task side
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    @JsonBackReference 
    private User assignedBy;

    private String attachments; // File URL/path

    
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    
    
}
