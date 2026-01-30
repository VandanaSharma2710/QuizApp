package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Answer;

import java.util.List;

public interface IAnswerService {
    Answer addAnswer(Long questionId, Answer answer);

    List<Answer> getAnswersByQuestion(Long questionId);

    Answer getCorrectAnswer(Long questionId);
}
