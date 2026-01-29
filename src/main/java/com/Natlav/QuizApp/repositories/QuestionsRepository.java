package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Question, Long> {
}
