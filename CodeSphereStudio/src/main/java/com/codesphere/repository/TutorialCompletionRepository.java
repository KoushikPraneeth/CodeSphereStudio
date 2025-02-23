package com.codesphere.repository;

import com.codesphere.model.TutorialCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialCompletionRepository extends JpaRepository<TutorialCompletion, Long> {
}