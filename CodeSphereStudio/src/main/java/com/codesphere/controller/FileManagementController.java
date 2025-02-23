package com.codesphere.controller;

import com.codesphere.model.ProjectFile;
import com.codesphere.service.FileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileManagementController {
    private final FileManagementService fileManagementService;

    @PostMapping
    public ResponseEntity<ProjectFile> createFile(
            @RequestBody ProjectFile file,
            Authentication authentication) {
        ProjectFile created = fileManagementService.createFile(
            authentication.getName(),
            file.getName(),
            file.getContent(),
            file.getLanguage()
        );
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ProjectFile>> getUserFiles(Authentication authentication) {
        return ResponseEntity.ok(fileManagementService.getUserFiles(authentication.getName()));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ProjectFile> getFile(@PathVariable Long fileId) {
        return fileManagementService.getFile(fileId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<ProjectFile> updateFile(
            @PathVariable Long fileId,
            @RequestBody ProjectFile file) {
        return ResponseEntity.ok(fileManagementService.updateFile(fileId, file.getContent()));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileManagementService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }
}