/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sibusiso.taskmanager.taskmanager.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @BeforeEach
    public void setup() throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        Files.walk(Paths.get(uploadDir))
            .filter(Files::isRegularFile)
            .forEach(path -> path.toFile().delete());
    }

    @Test
    public void testFileUploadAndDownload() throws IOException {
        Long taskId = 1L;
        String fileName = "test.txt";
        String content = "This is a test file.";

        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", fileName, "text/plain", content.getBytes()
        );

        
        String storedFileName = fileStorageService.storeFile(taskId, multipartFile);
        assertNotNull(storedFileName);

       Path filePath = Paths.get(uploadDir, taskId.toString(), storedFileName);
assertTrue(Files.exists(filePath), "Expected file to exist at: " + filePath);

        
        

        Resource resource = fileStorageService.loadFile(taskId, storedFileName);
        assertTrue(resource.exists());
assertEquals(storedFileName, resource.getFilename()); // ✅ Correct

        fileStorageService.deleteFile(taskId, storedFileName);
        assertFalse(Files.exists(filePath));
    }
}
