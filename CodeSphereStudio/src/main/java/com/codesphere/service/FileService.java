package com.codesphere.service;

import com.codesphere.model.User;
import com.codesphere.model.UserFile;
import com.codesphere.repository.UserFileRepository;
import com.codesphere.repository.UserRepository;
import com.codesphere.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileService {
    private final UserFileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(UserFileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public UserFile createFile(String username, String name, String content, String language) {
        User owner = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserFile file = new UserFile();
        file.setOwner(owner);
        file.setName(name);
        file.setPath("/" + username + "/" + name);
        file.setContent(content);
        file.setLanguage(language);

        return fileRepository.save(file);
    }

    public UserFile updateFile(Long fileId, String content) {
        UserFile file = fileRepository.findById(fileId)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        
        file.setContent(content);
        return fileRepository.save(file);
    }

    public List<UserFile> getUserFiles(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return fileRepository.findByOwnerId(user.getId());
    }

    public void deleteFile(Long fileId, String username) {
        UserFile file = fileRepository.findById(fileId)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        
        if (!file.getOwner().getUsername().equals(username)) {
            throw new org.springframework.security.access.AccessDeniedException("Not authorized to delete this file");
        }
        
        fileRepository.delete(file);
    }
}
