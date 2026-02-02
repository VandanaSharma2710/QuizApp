package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.entities.Quiz;

import java.util.List;

public interface IQuizService {

    Quiz createQuiz(Quiz quiz, Long createdBy);
    List<Quiz> getAllQuizzes();
    QuizResponse getQuizById(Long id);

    Quiz updateQuiz(Long quizId, Quiz quiz);
    void deleteQuiz(Long id);
}
