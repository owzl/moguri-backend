package org.moguri.event.quiz.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.event.quiz.domain.Quiz;
import org.moguri.event.quiz.repository.QuizMapper;
import org.moguri.event.quiz.service.QuizService;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizMapper quizMapper;

    @Override
    public List<Quiz> getQuizList() {
        return quizMapper.getQuizList();
    }

    @Override
    public Quiz getQuiz(long quizId) {
        return quizMapper.getQuiz(quizId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    @Override
    public void createQuiz(Quiz quiz) {
        try {
            quizMapper.createQuiz(quiz);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        try {
            quizMapper.updateQuiz(quiz);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    @Override
    public void deleteQuiz(long quizId) {
        try {
            quizMapper.deleteQuiz(quizId);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY);
        }
    }

}
