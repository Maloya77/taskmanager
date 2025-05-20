/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author ramph
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String username;
    private String email;

    @ManyToOne
@JoinColumn(name = "assigned_to_id", nullable = false)
private User assignedTo;

@ManyToOne
@JoinColumn(name = "assigned_by_id", nullable = false)
private User assignedBy;

@OneToMany(mappedBy = "assignedTo")
    @JsonIgnore
    private List<Task> tasks;
    


    // Getters, and Setters
public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }
    public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     // Constructors

    public User() {
    }

   

    public User(Long id, String fullName, String username, String email, User assignedTo, User assignedBy, List<Task> tasks) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.tasks = tasks;
    }

   
    
}

