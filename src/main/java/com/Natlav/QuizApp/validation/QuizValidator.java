package com.Natlav.QuizApp.validation;

import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.entities.Quiz;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuizValidator implements ConstraintValidator<ValidQuiz, Quiz> {

    @Override
    public boolean isValid(Quiz quiz, ConstraintValidatorContext context) {
        if(quiz == null) return true;


        //If quiz doesn't have any question
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Quiz must have at least one question").addConstraintViolation();
            return false;
        }


        //if any question has more or less than one correct answer
       for(int i=0; i<quiz.getQuestions().size(); i++){
           Question q = quiz.getQuestions().get(i);
           long correctCount = q.getAnswers().stream().filter(ans-> ans.isCorrect()).count();
           if(correctCount != 1){
               context.disableDefaultConstraintViolation();
               context.buildConstraintViolationWithTemplate(
                       "Question "+(i+1)+" must have exactly one correct answer"
               ).addConstraintViolation();
               return false;
           }

       }
        return true;
    }
}
