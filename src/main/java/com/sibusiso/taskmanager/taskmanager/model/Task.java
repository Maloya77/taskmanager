/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import jakarta.persistence.*;
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
    private int priority; // Example: 1 = High, 2 = Medium, 3 = Low

    @Column(nullable = false)
    private String dueDate; // We will change this to LocalDateTime later


}
