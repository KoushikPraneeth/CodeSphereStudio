package com.codesphere.controller;

import com.codesphere.model.Tutorial;
import com.codesphere.model.TutorialProgress;
import com.codesphere.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {
    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        return ResponseEntity.ok(tutorialService.getAllTutorials());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorial(@PathVariable Long id) {
        return tutorialService.getTutorial(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/progress")
    public ResponseEntity<Void> updateProgress(@RequestBody TutorialProgress progress) {
        tutorialService.updateProgress(progress);
        return ResponseEntity.ok().build();
    }
}
