package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Quiz;

import java.util.List;

public interface IQuizService {

    Quiz createQuiz(Quiz quiz, Long createdBy);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
    void deleteQuiz(Long id);
}
