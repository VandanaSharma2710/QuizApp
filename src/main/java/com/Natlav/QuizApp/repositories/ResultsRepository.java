package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.dto.QuizPerformanceResponse;
import com.Natlav.QuizApp.dto.TopPlayerResponse;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.Result;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Result, Long> {

    // Total attempts across all quizzes
    @Query("SELECT COUNT(r) FROM Result r")
    long countTotalAttempts();

    // Top players based on total score, pageable for limiting results (e.g., top 10)
    @Query("""
    SELECT new com.Natlav.QuizApp.dto.TopPlayerResponse(
        r.user.username,
        ROUND(SUM(r.score) * 1.0 / COUNT(r.id), 2),
        ROUND(SUM(r.totalScore) * 1.0 / COUNT(r.id), 2)
    )
    FROM Result r
    GROUP BY r.user.username
    ORDER BY (SUM(r.score) * 1.0 / SUM(r.totalScore)) DESC
""")
    List<TopPlayerResponse> findTopPlayers(Pageable pageable);

    // Quiz performance for dashboard
    @Query("""
    SELECT new com.Natlav.QuizApp.dto.QuizPerformanceResponse(
        r.quiz.title,
        COUNT(r),
        ROUND(AVG(r.score), 2)
    )
    FROM Result r
    GROUP BY r.quiz.id, r.quiz.title
    ORDER BY COUNT(r) DESC
""")
    List<QuizPerformanceResponse> getQuizPerformance();

    // Get all results of a particular user
    @Query("SELECT r FROM Result r WHERE r.user.id = :userId")
    List<Result> findAllByUserId(Long userId);

    // Get all results for a particular quiz
    @Query("SELECT r FROM Result r WHERE r.quiz.id = :quizId")
    List<Result> findAllByQuizId(Long quizId);

    // Count attempts for a specific quiz
    @Query("SELECT COUNT(r) FROM Result r WHERE r.quiz.id = :quizId")
    long countAttemptsByQuizId(Long quizId);

    // Get a user's highest score for a particular quiz
    @Query("SELECT MAX(r.score) FROM Result r WHERE r.user.id = :userId AND r.quiz.id = :quizId")
    Integer findHighestScoreByUserAndQuiz(Long userId, Long quizId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Result r WHERE r.quiz = :quiz")
    void deleteByQuiz(@Param("quiz") Quiz quiz);

}
