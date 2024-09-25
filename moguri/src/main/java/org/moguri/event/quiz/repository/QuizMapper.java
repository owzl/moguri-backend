package org.moguri.event.quiz.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.event.quiz.domain.Quiz;

import java.util.List;

@Mapper
public interface QuizMapper {

    int getTotalCount();

    List<Quiz> getQuizzes(PageRequest pageRequest);

    Quiz getQuiz(long quizId);

    void createQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

    void deleteQuiz(long quizId);

}