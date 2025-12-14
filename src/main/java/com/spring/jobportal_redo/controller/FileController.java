package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.dto.file.FileUploadResponseDto;
import com.spring.jobportal_redo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public FileUploadResponseDto uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam String folder
    ) {
        fileService.init(folder);
        return fileService.save(file, folder);
    }

}
