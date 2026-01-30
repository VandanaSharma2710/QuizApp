package com.Natlav.QuizApp.services.Implement;

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
    public Quiz getQuizById(Long id) {
        return _quizRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exists"));
    }

    @Override
    public void deleteQuiz(Long id) {
       Quiz quiz = _quizRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Quiz doesn't exist"));
        _quizRepository.delete(quiz);
    }
}
