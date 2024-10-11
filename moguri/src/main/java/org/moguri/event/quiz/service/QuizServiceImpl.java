package org.moguri.event.quiz.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.event.quiz.domain.Quiz;
import org.moguri.event.quiz.domain.QuizPart;
import org.moguri.event.quiz.repository.QuizMapper;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizMapper quizMapper;

    public int getTotalCount() {
        return quizMapper.getTotalCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Quiz> getQuizzes(PageRequest pageRequest) {
        return quizMapper.getQuizzes(pageRequest);
    }

    @Override
    public Quiz getQuiz(long quizId) {
        Quiz quiz = Optional.ofNullable(quizMapper.getQuiz(quizId)).orElseThrow(() -> new
                MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return quiz;
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

    @Override
    public boolean hasUserQuizPartToday(long memberId, long quizType) {
        return quizMapper.hasUserQuizPartToday(memberId, quizType);
    }

    @Override
    public void createQuizPartToday(QuizPart quizpart) {
        try {
            quizMapper.createQuizPartToday(quizpart);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }
}
