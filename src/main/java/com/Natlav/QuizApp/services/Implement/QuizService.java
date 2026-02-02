package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.dto.AnswerResponse;
import com.Natlav.QuizApp.dto.QuestionResponse;
import com.Natlav.QuizApp.dto.QuizResponse;
import com.Natlav.QuizApp.services.IQuizService;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.repositories.QuizzesRepository;
import com.Natlav.QuizApp.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService {

    private final QuizzesRepository _quizRepository;
    private final UsersRepository _userRepository;
    @Override
    public Quiz createQuiz(Quiz quiz, Long createdBy) {

        User user = _userRepository.findById(createdBy).orElseThrow(() -> new EntityNotFoundException("this user doesn't exist"));
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
    public Quiz updateQuiz(Long quizId, Quiz quiz) {
        Quiz existingQuiz =  _quizRepository.findById(quizId).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exists"));
         existingQuiz.setTitle(quiz.getTitle());
         existingQuiz.setDescription(quiz.getDescription());
         return _quizRepository.save(existingQuiz);
    }


    @Override
    public void deleteQuiz(Long id) {
       Quiz quiz = _quizRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exist"));
        _quizRepository.delete(quiz);
    }

    private QuizResponse MapToQuizResponse(Quiz quiz){
      return new QuizResponse(
              quiz.getId(),
              quiz.getTitle(),
              quiz.getDescription(),
              quiz.getQuestions().stream().map(q ->
                              new QuestionResponse(
                                      q.getId(),
                                      q.getQuestion(),
                                      q.getPoints(),
                                      q.getOrderIndex(),
                                      q.getAnswers().stream().map(a ->
                                              new AnswerResponse(a.getId(),
                                                      a.getOption_answer())
                                      ).toList()
                              )


                      ).toList()

      );
    }
}
