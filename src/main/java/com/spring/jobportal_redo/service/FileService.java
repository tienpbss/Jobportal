package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.dto.file.FileUploadResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class FileService {

    @Value("${spring.servlet.upload-file.base-path}")
    private String basePath;

    public void init(String folder) {
        Path path = Paths.get(basePath).resolve(folder);
        if (Files.exists(path) && Files.isDirectory(path)){
            return;
        }
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public FileUploadResponseDto save(MultipartFile file, String folder) {

        String fileName = String.format("%s_%s", Instant.now().toEpochMilli(), file.getOriginalFilename());
        Path filePath = Paths.get(basePath).resolve(folder).resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
            return new FileUploadResponseDto(fileName, Instant.now());
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }
}
