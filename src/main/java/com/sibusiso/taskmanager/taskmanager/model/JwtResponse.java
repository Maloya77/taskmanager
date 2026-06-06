/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import java.util.List;

/**
 *
 * @author ramph
 */
public class JwtResponse {
    
     private String token;
    private String username;
    private List<Role> roles;

    //Constructors
    public JwtResponse(String token, String username, List roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public JwtResponse() {
    }
    
   //Setters and Getters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
