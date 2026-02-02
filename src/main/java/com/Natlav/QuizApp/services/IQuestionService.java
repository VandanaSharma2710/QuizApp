package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.Question;

import java.util.List;

public interface IQuestionService {


        Question addQuestion(Long quizId, Question question);



        Question updateQuestions(Long questionId, Question question);

        void deleteQuestion(Long questionId);

}
