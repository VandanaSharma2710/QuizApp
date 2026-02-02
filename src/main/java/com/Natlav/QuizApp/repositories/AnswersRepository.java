package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Answer;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswersRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);

    Optional<Answer> findByQuestionIdAndIsCorrectTrue(Long questionId);

    @Modifying
    @Query("UPDATE Answer a SET a.isCorrect=false WHERE a.question.id =: questionId")
    void clearCorrectAnswerForQuestion(@Param("questionId") Long questionId);
}
