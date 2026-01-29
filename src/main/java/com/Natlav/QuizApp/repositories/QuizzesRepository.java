package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizzesRepository extends JpaRepository<Quiz, Long> {
}
