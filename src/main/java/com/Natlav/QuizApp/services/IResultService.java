package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.dto.QuizPerformanceResponse;
import com.Natlav.QuizApp.dto.ResultResponse;
import com.Natlav.QuizApp.dto.TopPlayerResponse;
import com.Natlav.QuizApp.entities.Result;
import com.Natlav.QuizApp.entities.User;

import java.util.List;
import java.util.Map;

public interface IResultService {
    ResultResponse submitQuiz(Long quizId, Map<Long, Long> answers, long timeTakenSeconds, User user);

    long getTotalAttempts();


     List<TopPlayerResponse> getTopPlayers(int topN) ;

     List<QuizPerformanceResponse> getQuizPerformance();

    List<Result> getResultsByUser(Long userId);

     List<Result> getResultsByQuiz(Long quizId);

    long getAttemptsByQuiz(Long quizId);

   Integer getHighestScore(Long userId, Long quizId);
}
