package com.codesphere.service;

import com.codesphere.model.ProjectFile;
import com.codesphere.repository.ProjectFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileManagementService {
    private final ProjectFileRepository projectFileRepository;

    @Transactional
    public ProjectFile createFile(String username, String name, String content, String language) {
        ProjectFile file = new ProjectFile();
        file.setName(name);
        file.setContent(content);
        file.setLanguage(language);
        file.setOwner(username);
        file.setCreatedAt(LocalDateTime.now());
        file.setLastModifiedAt(LocalDateTime.now());
        return projectFileRepository.save(file);
    }

    @Transactional
    public ProjectFile updateFile(Long fileId, String content) {
        ProjectFile file = projectFileRepository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));
        
        file.setContent(content);
        file.setLastModifiedAt(LocalDateTime.now());
        return projectFileRepository.save(file);
    }

    public List<ProjectFile> getUserFiles(String username) {
        return projectFileRepository.findByOwner(username);
    }

    public Optional<ProjectFile> getFile(Long fileId) {
        return projectFileRepository.findById(fileId);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        projectFileRepository.deleteById(fileId);
    }
}