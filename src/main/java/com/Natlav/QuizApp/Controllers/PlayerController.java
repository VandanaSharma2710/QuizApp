package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.dto.ResultResponse;
import com.Natlav.QuizApp.dto.SubmitQuiz;
import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.security.SecurityUtil;
import com.Natlav.QuizApp.services.Implement.QuizService;
import com.Natlav.QuizApp.services.Implement.ResultService;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
@PreAuthorize("hasRole('PLAYER')")
@RequiredArgsConstructor
public class PlayerController {

    private final QuizService quizService;
    private final ResultService resultService;
    private final SecurityUtil securityUtil;


    @GetMapping("/quizzes")
    public List<QuizResponse> getAllQuizzes(){
        return quizService.getAllQuizzes().stream().map(quizService::MapToQuizResponse).toList();
    }

    @GetMapping("/quizzes/{quizId}")
    public QuizResponse getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @PostMapping("/submit")
    public ResultResponse submitQuiz(@RequestParam Long quizId, @Valid @RequestBody SubmitQuiz submitQuiz, Authentication authentication){
        User user = securityUtil.getCurrentUser(authentication);
       return resultService.submitQuiz(quizId, submitQuiz.getAnswers(), submitQuiz.getTimeTakenSeconds(), user);
    }


}
