package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Question;


public interface IQuestionService {


        Question addQuestion(Long quizId, Question question);

        Question updateQuestions(Long questionId, Question question);

        void deleteQuestion(Long questionId);

}
