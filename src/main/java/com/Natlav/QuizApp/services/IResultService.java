package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Result;
import com.Natlav.QuizApp.entities.User;

import java.util.Map;

public interface IResultService {
    Result submitQuiz(Long quizId, Map<Long, Long> answers, User user);
}
