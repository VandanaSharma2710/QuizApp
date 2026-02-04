package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.security.SecurityUtil;
import com.Natlav.QuizApp.services.Implement.AnswerService;
import com.Natlav.QuizApp.services.Implement.QuestionService;
import com.Natlav.QuizApp.services.Implement.QuizService;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.entities.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdBy;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
public class GameMasterController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final SecurityUtil securityUtil;

    @GetMapping("/quizzes")
    public List<QuizResponse> getAllQuizzesForAdmin(){
        return quizService.getAllQuizzes().stream().map(quizService::MapToQuizResponse).toList();
    }

    @PostMapping("/quizzes")
    @PreAuthorize("hasRole('GAMEMASTER')")
    public QuizResponse createQuiz(@RequestBody Quiz quiz, Authentication authentication){
        User user = securityUtil.getCurrentUser(authentication);
        Quiz savedQuiz = quizService.createQuiz(quiz, user);
        return  quizService.MapToQuizResponse(savedQuiz);

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

    @PutMapping("/quizzes/{quizId}")
    public Quiz updateQuiz(@PathVariable Long quizId, @RequestBody Quiz quiz){
        return quizService.updateQuiz(quizId, quiz);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId, @RequestBody Question question){
        return questionService.updateQuestions(questionId, question);
    }

    @PutMapping("/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long answerId, @RequestBody Answer answer){
        return answerService.updateAnswer(answerId, answer);
    }


    @GetMapping("/question/{questionId}/correct-answer")
    public Answer getCorrectAnswer(@PathVariable Long questionId){
        return answerService.getCorrectAnswer(questionId);
    }

    @DeleteMapping("/quizzes/{quizId}")
    public void deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
    }

    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
    }

    @DeleteMapping("/answers/{answerId}")
    public void deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
    }


    @GetMapping("/quizzes/{quizId}")
    public QuizResponse getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }
}
