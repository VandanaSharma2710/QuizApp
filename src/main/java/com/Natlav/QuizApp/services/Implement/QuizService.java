package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.dto.AnswerResponse;
import com.Natlav.QuizApp.dto.QuestionResponse;
import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.repositories.ResultsRepository;
import com.Natlav.QuizApp.services.IQuizService;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.repositories.QuizzesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class QuizService implements IQuizService {

    private final QuizzesRepository _quizRepository;
    private final ResultsRepository _resultsRepository;


    @Override
    public Quiz createQuiz(Quiz quiz, User user) {
        boolean exists = _quizRepository.existsByTitleAndCreatedBy(quiz.getTitle(), user);
        if(exists) {
            throw new RuntimeException("Quiz already exists");
        }

        for(Question question:quiz.getQuestions()){
            question.setQuiz(quiz);
            if(question.getAnswers().stream().filter(Answer::isCorrect).count() != 1){
                throw new RuntimeException("Question must have exactly one correct answer");
            }
            for(Answer answer: question.getAnswers()){
                answer.setQuestion(question);
            }
        }


        quiz.setCreatedBy(user);
        return _quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {

        return _quizRepository.findAll();
    }

    @Override
    public QuizResponse getQuizById(Long id) {
        Quiz existingQuiz = _quizRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exists"));
         return MapToQuizResponse(existingQuiz);
    }

    @Override
    @Transactional
    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz) {

        Quiz existingQuiz = _quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        existingQuiz.setTitle(updatedQuiz.getTitle());
        existingQuiz.setDescription(updatedQuiz.getDescription());


        existingQuiz.getQuestions().clear();

        for (Question q : updatedQuiz.getQuestions()) {


            if (q.getAnswers().stream().filter(Answer::isCorrect).count() != 1) {
                throw new RuntimeException("Each question must have exactly one correct answer");
            }
            q.setId(null);
            q.setQuiz(existingQuiz);

            for (Answer a : q.getAnswers()) {
                a.setId(null);
                a.setQuestion(q);
            }
        }

        existingQuiz.getQuestions().addAll(updatedQuiz.getQuestions());

        return _quizRepository.save(existingQuiz);
    }



    @Transactional
    @Override
    public void deleteQuiz(Long id) {
       Quiz quiz = _quizRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exist"));
        _resultsRepository.deleteByQuiz(quiz);
        _quizRepository.delete(quiz);
    }

    public QuizResponse MapToQuizResponse(Quiz quiz){
      return new QuizResponse(
              quiz.getId(),
              quiz.getTitle(),
              quiz.getDescription(),
              quiz.getTimeLimitSeconds(),
              quiz.getQuestions().stream().map(q ->
                              new QuestionResponse(
                                      q.getId(),
                                      q.getQuestion(),
                                      q.getPoints(),
                                      q.getOrderIndex(),
                                      q.getAnswers().stream().map(a ->
                                              new AnswerResponse(a.getId(),
                                                      a.getOption_answer(), a.isCorrect())
                                      ).toList()
                              )


                      ).toList()

      );
    }
}
