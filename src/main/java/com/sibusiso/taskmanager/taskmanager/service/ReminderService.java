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

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            boolean taskUpdated = false;

            if (task.getDueDate() != null && task.getStatus() != TaskStatus.COMPLETED) {

                // Enforced 12-hour reminder if due in 3 days or less
                if (task.getDueDate().isBefore(now.plusDays(3))) {
                    boolean shouldSend = task.getLastReminderSent() == null ||
                            task.getLastReminderSent().isBefore(now.minusHours(12));

                    if (shouldSend) {
                        sendReminder(task);
                        task.setLastReminderSent(now);
                        taskUpdated = true;
                    }
                }

                // Custom reminder
                if (task.getCustomReminderTime() != null &&
                        task.getCustomReminderTime().isBefore(now)) {
                    sendReminder(task);
                    task.setCustomReminderTime(null); // prevent repeat
                    taskUpdated = true;
                }
            }

            if (taskUpdated) {
                taskRepository.save(task); // Save only if reminder info changed
            }
        }
    }

    private void sendReminder(Task task) {
        // Log or system alert (console)
        logger.info("Reminder triggered for task: " + task.getTitle());

        // Save task changes (e.g., lastReminderSent)
        taskRepository.save(task);

        // Email sending logic
        if (task.getAssignedTo() != null && task.getAssignedTo().getEmail() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(task.getAssignedTo().getEmail());
            message.setSubject("Task Reminder: " + task.getTitle());
            message.setText("Hi " + task.getAssignedTo().getFullName() +
                ",\n\nThis is a reminder that your task \"" + task.getTitle() +
                "\" is due on " + task.getDueDate() + ".\n\nPlease ensure it's completed on time.\n\nRegards,\nTask Manager System");

            mailSender.send(message);
        } else {
            logger.info("No email assigned for this task.");
        }
    }
}