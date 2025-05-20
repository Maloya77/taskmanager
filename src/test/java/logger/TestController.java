/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logger;

import com.sibusiso.taskmanager.taskmanager.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ramph
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/run-reminder")
    public String runReminderNow() {
        reminderService.checkForReminders();
        return "Reminder check executed.";
    }
}

