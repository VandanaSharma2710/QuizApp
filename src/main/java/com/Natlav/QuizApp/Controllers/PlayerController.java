package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.services.Implement.AnswerService;
import com.Natlav.QuizApp.services.Implement.QuestionService;
import com.Natlav.QuizApp.services.Implement.QuizService;
import com.Natlav.QuizApp.services.Implement.ResultService;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final QuizService quizService;
    private final ResultService resultService;

    @GetMapping("/home")
    public String home(Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String name = oauthUser.getAttribute("name");

        return "Welcome " + name + " 🎉 login successfully";

    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }

    @GetMapping("quizzes/{quizId}")
    public QuizResponse getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @PostMapping("/submit")
    public Result submitQuiz(@RequestParam Long userId, @RequestParam Long quizId, @RequestBody Map<Long, Long> answers){
       return resultService.submitQuiz(userId, quizId, answers);
    }


}
