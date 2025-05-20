/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 *
 * @author ramph
 */

@SpringBootApplication
@EnableScheduling
public class TaskManagerApplication {
     public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
