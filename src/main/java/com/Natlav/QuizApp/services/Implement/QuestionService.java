package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.services.IQuestionService;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.entities.Quiz;
import com.Natlav.QuizApp.repositories.QuestionsRepository;
import com.Natlav.QuizApp.repositories.QuizzesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionsRepository _questionRepository;
    private final QuizzesRepository _quizRespository;

    @Override
    public Question addQuestion(Long quizId, Question question) {

        Quiz quiz = _quizRespository.findById(quizId).orElseThrow(()-> new EntityNotFoundException("Quiz not found"));
        question.setQuiz(quiz);
        return _questionRepository.save(question);

    }

    @Override
    public List<Question> getQuestionsByQuiz(Long quizId) {

        return _questionRepository.findByQuizId(quizId);

    }

    @Override
    public Question updateQuestions(Long questionId, Question question) {
        Question existing = _questionRepository.findById(questionId).orElseThrow(()->new EntityNotFoundException("Question doesn't exist"));
        existing.setQuestion(question.getQuestion());
        existing.setPoints(question.getPoints());
        existing.setOrderIndex(question.getOrderIndex());
        return _questionRepository.save(existing);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question existing = _questionRepository.findById(questionId).orElseThrow(()->new EntityNotFoundException("Question doesn't exist"));
        _questionRepository.delete(existing);
    }
}
