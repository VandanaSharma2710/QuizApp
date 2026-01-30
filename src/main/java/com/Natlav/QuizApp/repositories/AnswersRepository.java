package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswersRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);

    Optional<Answer> findByQuestionIdAndIsCorrectTrue(Long questionId);
}
