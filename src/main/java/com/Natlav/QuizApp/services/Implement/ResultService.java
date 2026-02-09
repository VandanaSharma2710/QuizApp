package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.dto.QuizPerformanceResponse;
import com.Natlav.QuizApp.dto.ResultResponse;
import com.Natlav.QuizApp.dto.TopPlayerResponse;
import com.Natlav.QuizApp.services.IResultService;
import com.Natlav.QuizApp.entities.*;
import com.Natlav.QuizApp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultService implements IResultService {

    private final UsersRepository usersRepository;
    private final QuizzesRepository quizzesRepository;
    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;
    private final ResultsRepository resultsRepository;

    @Override
    public ResultResponse submitQuiz(Long quizId, Map<Long, Long> answers, long timeTakenSeconds, User user) {

        Quiz quiz = quizzesRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        if(timeTakenSeconds>quiz.getTimeLimitSeconds()){
           throw  new IllegalArgumentException("Time limit exceeded: allowed :"+quiz.getTimeLimitSeconds()+"seconds");
        }

        List<Question> questions = questionsRepository.findByQuizId(quizId);

        int score = 0;
        int totalScore =0;
        for (Question question : questions){

            totalScore += question.getPoints();

            Long selectedAnswerId = answers.get(question.getId());
            if(selectedAnswerId == null)
                continue;
            Answer correctAnswer = answersRepository.findByQuestionIdAndIsCorrectTrue(question.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Correct answer not set"));
            if(selectedAnswerId.equals(correctAnswer.getId())){
                score += question.getPoints();
            }

        }


        Result result = new Result();
        result.setUser(user);
        result.setQuiz(quiz);
        result.setScore(score);
        result.setTotalScore(totalScore);
        result.setStartedAt(LocalDateTime.now().minusSeconds(timeTakenSeconds));
        result.setCompletedAt(LocalDateTime.now());

        Result savedResponse= resultsRepository.save(result);
        return  new ResultResponse(
                savedResponse.getId(),
                savedResponse.getScore(),
                savedResponse.getTotalScore(),
                savedResponse.getQuiz().getId()
        );
    }


    public long getTotalAttempts() {
        return resultsRepository.countTotalAttempts();
    }

    // Top N players
    public List<TopPlayerResponse> getTopPlayers(int topN) {
        return resultsRepository.findTopPlayers(PageRequest.of(0, topN));
    }

    // Quiz performance (attempts + average score)
    public List<QuizPerformanceResponse> getQuizPerformance() {
        return resultsRepository.getQuizPerformance();
    }

    // All results of a particular user
    public List<Result> getResultsByUser(Long userId) {
        return resultsRepository.findAllByUserId(userId);
    }

    // All results of a particular quiz
    public List<Result> getResultsByQuiz(Long quizId) {
        return resultsRepository.findAllByQuizId(quizId);
    }

    // Count attempts for a specific quiz
    public long getAttemptsByQuiz(Long quizId) {
        return resultsRepository.countAttemptsByQuizId(quizId);
    }

    // User's highest score in a specific quiz
    public Integer getHighestScore(Long userId, Long quizId) {
        return resultsRepository.findHighestScoreByUserAndQuiz(userId, quizId);
    }
}
