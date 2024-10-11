package org.moguri.event.quiz.service;

import org.moguri.common.response.PageRequest;
import org.moguri.event.quiz.domain.Quiz;
import org.moguri.event.quiz.domain.QuizPart;

import java.util.List;

public interface QuizService {

    List<Quiz> getQuizzes(PageRequest pageRequest);

    Quiz getQuiz(long quizId);

    void createQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

    void deleteQuiz(long quizId);

    int getTotalCount();

    boolean hasUserQuizPartToday(long memberId, long quizType);

    void createQuizPartToday(QuizPart quizpart);
}
