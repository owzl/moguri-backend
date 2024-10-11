package org.moguri.event.quiz.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.event.quiz.domain.Quiz;
import org.moguri.event.quiz.domain.QuizPart;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface QuizMapper {

    int getTotalCount();

    List<Quiz> getQuizzes(PageRequest pageRequest);

    Quiz getQuiz(long quizId);

    void createQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

    void deleteQuiz(long quizId);

    boolean hasUserQuizPartToday(@Param("memberId") long memberId, @Param("quizType") long quizType);

    void createQuizPartToday(QuizPart quizpart);
}