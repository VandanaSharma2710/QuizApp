package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Result;

import java.util.Map;

public interface IResultService {
    Result submitQuiz(Long userId, Long quizId, Map<Long, Long> answers);
}
