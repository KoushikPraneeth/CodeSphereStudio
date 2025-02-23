package com.codesphere.service;

import com.codesphere.model.*;
import com.codesphere.repository.TutorialRepository;
import com.codesphere.repository.UserRepository;
import com.codesphere.repository.TutorialCompletionRepository;
import com.codesphere.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TutorialService {
    private final TutorialRepository tutorialRepository;
    private final CodeExecutionService codeExecutionService;
    private final UserRepository userRepository;
    private final TutorialCompletionRepository tutorialCompletionRepository;

    public TutorialService(TutorialRepository tutorialRepository,
                          CodeExecutionService codeExecutionService,
                          UserRepository userRepository,
                          TutorialCompletionRepository tutorialCompletionRepository) {
        this.tutorialRepository = tutorialRepository;
        this.codeExecutionService = codeExecutionService;
        this.userRepository = userRepository;
        this.tutorialCompletionRepository = tutorialCompletionRepository;
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public Tutorial createTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public Optional<Tutorial> getTutorial(Long id) {
        return tutorialRepository.findById(id);
    }

    public List<Tutorial> getTutorialsByCategory(String category) {
        return tutorialRepository.findByCategory(category);
    }

    public void updateProgress(TutorialProgress progress) {
        Tutorial tutorial = tutorialRepository.findById(progress.getTutorial().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Tutorial not found"));
        User user = userRepository.findById(progress.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        progress.setTutorial(tutorial);
        progress.setUser(user);
        // Add logic to save progress if needed
    }

    public TutorialProgress validateStep(Long tutorialId, Integer stepIndex, String code, String username) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
            .orElseThrow(() -> new ResourceNotFoundException("Tutorial not found"));
        
        if (stepIndex >= tutorial.getSteps().size()) {
            throw new IllegalArgumentException("Invalid step index");
        }
        
        TutorialStep step = tutorial.getSteps().get(stepIndex);
        
        // Create a request with the code, language, and input
        CodeExecutionRequest request = new CodeExecutionRequest();
        request.setCode(code);
        request.setLanguage(step.getLanguage()); // Assuming TutorialStep has a language field
        request.setInput("");
        
        ExecutionResult result = codeExecutionService.executeCode(request);
        boolean passed = validateStepOutput(result.getOutput(), step.getExpectedOutput());
        
        if (passed) {
            updateUserProgress(tutorialId, stepIndex, username);
        }
        
        return TutorialProgress.builder()
            .user(userRepository.findByUsername(username).orElse(null))
            .tutorial(tutorial)
            .currentStep(stepIndex)
            .completed(passed)
            .build();
    }

    private void updateUserProgress(Long tutorialId, Integer stepIndex, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
            .orElseThrow(() -> new ResourceNotFoundException("Tutorial not found"));

        TutorialCompletion completion = TutorialCompletion.builder()
            .user(user)
            .tutorial(tutorial)
            .stepIndex(stepIndex)
            .completedAt(LocalDateTime.now())
            .build();
        
        tutorialCompletionRepository.save(completion);
    }

    private boolean validateStepOutput(String actual, String expected) {
        return actual != null && actual.trim().equals(expected.trim());
    }
}
