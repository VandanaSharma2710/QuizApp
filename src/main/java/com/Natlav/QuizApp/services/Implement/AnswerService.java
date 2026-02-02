package com.Natlav.QuizApp.services.Implement;

import com.Natlav.QuizApp.services.IAnswerService;
import com.Natlav.QuizApp.entities.Answer;
import com.Natlav.QuizApp.entities.Question;
import com.Natlav.QuizApp.repositories.AnswersRepository;
import com.Natlav.QuizApp.repositories.QuestionsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Answer getCorrectAnswer(Long questionId) {
        return answersRepository.findByQuestionIdAndIsCorrectTrue(questionId).orElseThrow(()-> new EntityNotFoundException("Correct answer not found"));
    }

    @Transactional
    @Override
    public Answer updateAnswer(Long answerId, Answer updatedAnswer) {
        Answer existAnswer = answersRepository.findById(answerId).orElseThrow(()-> new EntityNotFoundException("answer doesn't exist"));
        if(updatedAnswer.isCorrect()){
             answersRepository.clearCorrectAnswerForQuestion(existAnswer.getQuestion().getId());
        }
        existAnswer.setOption_answer(updatedAnswer.getOption_answer());
        existAnswer.setCorrect(updatedAnswer.isCorrect());
        return answersRepository.save(existAnswer);
    }

    @Override
    public void deleteAnswer(Long answerId) {
        Answer existAnswer = answersRepository.findById(answerId).orElseThrow(()-> new EntityNotFoundException("answer doesn't exist"));
        answersRepository.delete(existAnswer);
    }
}
