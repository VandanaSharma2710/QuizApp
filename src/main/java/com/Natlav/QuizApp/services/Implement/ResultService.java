package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.services.IResultService;
import com.Natlav.QuizApp.entities.*;
import com.Natlav.QuizApp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public Result submitQuiz(Long quizId, Map<Long, Long> answers, User user) {

        Quiz quiz = quizzesRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

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
        result.setStartedAt(LocalDateTime.now());     // Later from frontend
        result.setCompletedAt(LocalDateTime.now());

        return resultsRepository.save(result);
    }
}
