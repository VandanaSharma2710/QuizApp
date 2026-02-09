package com.Natlav.QuizApp.Controllers;

import com.Natlav.QuizApp.dto.DashboardStatResponse;
import com.Natlav.QuizApp.dto.QuizPerformanceResponse;
import com.Natlav.QuizApp.dto.TopPlayerResponse;
import com.Natlav.QuizApp.entities.Result;
import com.Natlav.QuizApp.enums.RoleType;
import com.Natlav.QuizApp.repositories.QuizzesRepository;
import com.Natlav.QuizApp.repositories.UsersRepository;
import com.Natlav.QuizApp.services.Implement.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/master/dashboard")
@PreAuthorize("hasRole('GAMEMASTER')")
public class AdminDashBoardController {

    private final ResultService resultService;
    private final QuizzesRepository quizzesRepository;
    private final UsersRepository usersRepository;


    @GetMapping("/stats")
    public DashboardStatResponse getStats() {
        long totalQuizzes = quizzesRepository.count();
        long totalPlayers = usersRepository.countPlayers(RoleType.ROLE_PLAYER);
        long totalAttempts = resultService.getTotalAttempts();

        return new DashboardStatResponse(totalQuizzes, totalPlayers, totalAttempts);
    }


    @GetMapping("/top-players")
    public List<TopPlayerResponse> getTopPlayers() {
        return resultService.getTopPlayers(5);
    }

    @GetMapping("/quiz-performance")
    public List<QuizPerformanceResponse> getQuizPerformance() {
        return resultService.getQuizPerformance();
    }


    @GetMapping("/user-results/{userId}")
    public List<Result> getResultsByUser(@PathVariable Long userId) {
        return resultService.getResultsByUser(userId);
    }


    @GetMapping("/quiz-results/{quizId}")
    public List<Result> getResultsByQuiz(@PathVariable Long quizId) {
        return resultService.getResultsByQuiz(quizId);
    }

    @GetMapping("/quiz-attempts/{quizId}")
    public long getAttemptsByQuiz(@PathVariable Long quizId) {
        return resultService.getAttemptsByQuiz(quizId);
    }


    @GetMapping("/user-highscore/{userId}/{quizId}")
    public Integer getHighestScore(@PathVariable Long userId, @PathVariable Long quizId) {
        return resultService.getHighestScore(userId, quizId);
    }
}
