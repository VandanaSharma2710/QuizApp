package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.services.Implement.AnswerService;
import com.Natlav.QuizApp.services.Implement.QuestionService;
import com.Natlav.QuizApp.services.Implement.QuizService;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.entities.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
public class GameMasterController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/quizzes")
    public Quiz createQuiz(@RequestBody Quiz quiz, @RequestParam Long createdBy){
        return quizService.createQuiz(quiz, createdBy);
    }


    @PostMapping("/quizzes/{quizId}/questions")
    public Question addQuestion(@PathVariable Long quizId,
                                @RequestBody Question question) {
        return questionService.addQuestion(quizId, question);
    }


    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId,
                            @RequestBody Answer answer) {
        return answerService.addAnswer(questionId, answer);
    }


    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
    }


    @DeleteMapping("/quizzes/{quizId}")
    public void deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
    }
}
