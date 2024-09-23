package org.moguri.event.quiz.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.quiz.domain.Quiz;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mapper
public interface QuizMapper {

    List<Quiz> getQuizList();

    Optional<Quiz> getQuiz(long quizId);

    void createQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

    void deleteQuiz(long quizId);

}