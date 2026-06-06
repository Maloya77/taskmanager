/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.controller;

import com.sibusiso.taskmanager.taskmanager.dto.RegisterRequest;
import com.sibusiso.taskmanager.taskmanager.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ramph
 */

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token) {
        return registrationService.verify(token);
    }
}