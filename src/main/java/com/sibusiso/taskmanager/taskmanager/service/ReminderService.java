/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import com.sibusiso.taskmanager.taskmanager.model.Task;
import com.sibusiso.taskmanager.taskmanager.model.TaskStatus;
import com.sibusiso.taskmanager.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ramph
 */
@Service
public class ReminderService {

    private static final Logger logger = LoggerFactory.getLogger(ReminderService.class);

    @Autowired
    private TaskRepository taskRepository;

     @Autowired
    private JavaMailSender mailSender;
     
    // Runs every hour (3600000ms)
    @Scheduled(fixedRate = 3600000)
    @Transactional
public void checkForReminders() {
    LocalDateTime now = LocalDateTime.now();
    logger.info("Starting reminder check at {}", now);

    List<Task> tasks = taskRepository.findAll();

    for (Task task : tasks) {
        boolean taskUpdated = false;

        if (task.getDueDate() != null && task.getStatus() != TaskStatus.COMPLETED) {
            if (task.getDueDate().isBefore(now.plusDays(3))) {
                boolean shouldSend = task.getLastReminderSent() == null ||
                        task.getLastReminderSent().isBefore(now.minusHours(12));

                if (shouldSend) {
                    sendReminder(task);
                    task.setLastReminderSent(now);
                    taskUpdated = true;
                }
            }

            if (task.getCustomReminderTime() != null &&
                    task.getCustomReminderTime().isBefore(now)) {
                sendReminder(task);
                task.setCustomReminderTime(null); // prevent repeat
                taskUpdated = true;
            }
        }

        if (taskUpdated) {
            taskRepository.save(task);
        }
    }

    logger.info("Reminder check complete at {}", LocalDateTime.now());
}


    private void sendReminder(Task task) {
    logger.info("Reminder triggered for task: {}", task.getTitle());

    try {
        if (task.getAssignedTo() != null && task.getAssignedTo().getEmail() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(task.getAssignedTo().getEmail());
            message.setSubject("Task Reminder: " + task.getTitle());
            message.setText("Hi " + task.getAssignedTo().getFullName() +
                    ",\n\nThis is a reminder that your task \"" + task.getTitle() +
                    "\" is due on " + task.getDueDate() + ".\n\nPlease ensure it's completed on time.\n\nRegards,\nTask Manager System");

            logger.info("Sending email to {}", task.getAssignedTo().getEmail());
            mailSender.send(message);
            logger.info("Email sent successfully to {}", task.getAssignedTo().getEmail());
        } else {
            logger.info("No email assigned for this task.");
        }
    } catch (Exception e) {
        logger.error("Failed to send email reminder for task '{}': {}", task.getTitle(), e.getMessage());
    }
}

}