package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.services.IAnswerService;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.repositories.AnswersRepository;
import com.Natlav.QuizApp.repositories.QuestionsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {

    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;
    @Override
    public Answer addAnswer(Long questionId, Answer answer) {
        Question question = questionsRepository.findById(questionId).orElseThrow(()->new EntityNotFoundException("Question not found"));
        answer.setQuestion(question);
        return answersRepository.save(answer);

    }

    @Override
    public List<Answer> getAnswersByQuestion(Long questionId) {

        return answersRepository.findByQuestionId(questionId);

    }

    @Override
    public Answer getCorrectAnswer(Long questionId) {
        return answersRepository.findByQuestionIdAndIsCorrectTrue(questionId).orElseThrow(()-> new EntityNotFoundException("Correct answer not found"));
    }
}
