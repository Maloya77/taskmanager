/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author ramph
 */
public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;
    
    @JsonCreator
    public static TaskStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        return TaskStatus.valueOf(value.toUpperCase().replace(" ", "_"));
    }
    
     @JsonValue
    public String toValue() {
        return this.name();
    }
}
