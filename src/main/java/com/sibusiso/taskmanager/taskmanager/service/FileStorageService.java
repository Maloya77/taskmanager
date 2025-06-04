/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
/**
 *
 * @author ramph
 */
@Service
public class FileStorageService {

  
    private final Path rootLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }

    public String storeFile(Long taskId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path taskDir = rootLocation.resolve(String.valueOf(taskId));
            Files.createDirectories(taskDir);
            Files.copy(file.getInputStream(), taskDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Resource loadFile(Long taskId, String filename) {
        try {
            Path file = rootLocation.resolve(String.valueOf(taskId)).resolve(filename);
            Resource resource = new org.springframework.core.io.UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public void deleteFile(Long taskId, String filename) throws IOException {
        Path file = rootLocation.resolve(String.valueOf(taskId)).resolve(filename);
        Files.deleteIfExists(file);
    }
}
