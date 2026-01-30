package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);
}
