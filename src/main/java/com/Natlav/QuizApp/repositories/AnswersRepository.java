package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswersRepository extends JpaRepository<Answer, Long> {
}
