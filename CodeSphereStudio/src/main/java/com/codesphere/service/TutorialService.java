package com.codesphere.service;

import com.codesphere.model.Tutorial;
import com.codesphere.model.TutorialStep;
import com.codesphere.model.TutorialCompletion;
import com.codesphere.model.User;
import com.codesphere.repository.TutorialRepository;
import com.codesphere.repository.UserRepository;
import com.codesphere.repository.TutorialCompletionRepository;
import com.codesphere.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepository tutorialRepository;
    private final CodeExecutionService codeExecutionService;
    private final UserRepository userRepository;
    private final TutorialCompletionRepository tutorialCompletionRepository;

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

    public TutorialProgress validateStep(Long tutorialId, Integer stepIndex, String code, String username) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
            .orElseThrow(() -> new ResourceNotFoundException("Tutorial not found"));
        
        TutorialStep step = tutorial.getSteps().get(stepIndex);
        ExecutionResult result = codeExecutionService.executeCode(code, "python"); // Add language parameter
        
        boolean passed = validateStepOutput(result.getOutput(), step.getExpectedOutput());
        
        if (passed) {
            updateUserProgress(tutorialId, stepIndex, username);
        }
        
        return TutorialProgress.builder()
            .passed(passed)
            .output(result.getOutput())
            .message(passed ? "Great job!" : "Try again!")
            .build();
    }

    private void updateUserProgress(Long tutorialId, Integer stepIndex, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        TutorialCompletion completion = TutorialCompletion.builder()
            .user(user)
            .tutorialId(tutorialId)
            .stepIndex(stepIndex)
            .completedAt(LocalDateTime.now())
            .build();
        
        tutorialCompletionRepository.save(completion);
    }

    private boolean validateStepOutput(String actual, String expected) {
        return actual.trim().equals(expected.trim());
    }
}
