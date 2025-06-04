/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.controller;

import com.sibusiso.taskmanager.taskmanager.dto.JwtResponse;
import com.sibusiso.taskmanager.taskmanager.dto.LoginRequest;
import com.sibusiso.taskmanager.taskmanager.dto.RegisterRequest;
import com.sibusiso.taskmanager.taskmanager.model.User;
import com.sibusiso.taskmanager.taskmanager.service.JwtService;
import com.sibusiso.taskmanager.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author ramph
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwtService = jwtService;
}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        User user = (User) authentication.getPrincipal(); // Assumes User implements UserDetails
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}