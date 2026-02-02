package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Answer;

import java.util.List;

public interface IAnswerService {
    Answer addAnswer(Long questionId, Answer answer);

    Answer getCorrectAnswer(Long questionId);

    Answer updateAnswer(Long answerId, Answer answer);

    void deleteAnswer(Long answerId);
}
