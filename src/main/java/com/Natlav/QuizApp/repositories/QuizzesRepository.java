package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizzesRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions ques LEFT JOIN FETCH ques.answers WHERE q.id = :quizId")
    Optional<Quiz> findQuizWithQuestionsAnswers(@Param("quizId") Long quizId);
}
