package org.moguri.event.quiz.service;

import org.moguri.event.quiz.domain.Quiz;

import java.util.List;

public interface QuizService {

    List<Quiz> getQuizList();

    Quiz getQuiz(long quizId);

    void createQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

    void deleteQuiz(long quizId);
}
