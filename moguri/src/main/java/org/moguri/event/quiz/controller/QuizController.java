package org.moguri.event.quiz.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.event.quiz.domain.Quiz;
import org.moguri.event.quiz.param.QuizCreateParam;
import org.moguri.event.quiz.param.QuizUpdateParam;
import org.moguri.event.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("")
    public ApiResponse<MoguriPage<Quiz>> getQuizzes(QuizGetRequest request) {

        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Quiz> quizzes = quizService.getQuizzes(pageRequest);
        int totalCount = quizService.getTotalCount();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                quizzes.stream().map(QuizItem::of).toList()));
    }

    @GetMapping("/{quizId}")
    public ApiResponse<QuizItem> getQuiz(@PathVariable long quizId) {

        Quiz quiz = quizService.getQuiz(quizId);

        return ApiResponse.of(QuizItem.of(quiz));
    }

    @PostMapping("")
    public ApiResponse<ReturnCode> create(@RequestBody QuizCreateRequest request) {
        QuizCreateParam param = request.convert();
        quizService.createQuiz(param.toEntity());

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PatchMapping("/{quizId}")
    public ApiResponse<ReturnCode> update(@PathVariable long quizId, @RequestBody QuizUpdateRequest request) {
        request.setQuizId(quizId);
        QuizUpdateParam param = request.convert();
        quizService.updateQuiz(param.toEntity());

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @DeleteMapping("/{quizId}")
    public ApiResponse<ReturnCode> delete(@PathVariable long quizId) {
        quizService.deleteQuiz(quizId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    public static class QuizGetRequest {

        private int page = 0;
        private int limit = 30;
        //default 값
    }

    @Data
    private static class QuizItem {

        private long quizId;
        private String question;
        private int type;
        private String example1;
        private String example2;
        private String example3;
        private String example4;
        private String answer;

        private static QuizItem of(Quiz quiz) {
            QuizItem converted = new QuizItem();
            converted.setQuizId(quiz.getQuizId());
            converted.setQuestion(quiz.getQuestion());
            converted.setType(quiz.getType());
            converted.setExample1(quiz.getExample1());
            converted.setExample2(quiz.getExample2());
            converted.setExample3(quiz.getExample3());
            converted.setExample4(quiz.getExample4());
            converted.setAnswer(quiz.getAnswer());
            return converted;
        }
    }

    @Data
    public static class QuizCreateRequest {
        private String question;
        private int type;
        private String example1;
        private String example2;
        private String example3;
        private String example4;
        private String answer;

        public QuizCreateParam convert() {
            return QuizCreateParam.builder()
                    .question(question)
                    .type(type)
                    .example1(example1)
                    .example2(example2)
                    .example3(example3)
                    .example4(example4)
                    .answer(answer)
                    .build();
        }
    }

    @Data
    public static class QuizUpdateRequest {

        private long quizId;
        private String question;
        private int type;
        private String example1;
        private String example2;
        private String example3;
        private String example4;
        private String answer;

        public QuizUpdateParam convert() {
            return QuizUpdateParam.builder()
                    .quizId(quizId)
                    .question(question)
                    .type(type)
                    .example1(example1)
                    .example2(example2)
                    .example3(example3)
                    .example4(example4)
                    .answer(answer)
                    .build();
        }
    }
}
