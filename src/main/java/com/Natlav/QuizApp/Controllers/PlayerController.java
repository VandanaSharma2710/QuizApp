package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.services.Implement.AnswerService;
import com.Natlav.QuizApp.services.Implement.QuestionService;
import com.Natlav.QuizApp.services.Implement.QuizService;
import com.Natlav.QuizApp.services.Implement.ResultService;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ResultService resultService;

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }

    @GetMapping("/quizzes/{quizId}/questions")
    public List<Question> getQuestions(@PathVariable Long quizId){
        return  questionService.getQuestionsByQuiz(quizId);
    }

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswers(@PathVariable Long questionId){
      return answerService.getAnswersByQuestion(questionId);
    }

    @PostMapping("/submit")
    public Result submitQuiz(@RequestParam Long userId, @RequestParam Long quizId, @RequestBody Map<Long, Long> answers){
       return resultService.submitQuiz(userId, quizId, answers);
    }


}
