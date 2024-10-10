package org.moguri.accountbook.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.dto.AccountBooksResponse;
import org.moguri.accountbook.param.AccountBookCreateParam;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.service.AccountBookService;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/accountbook")
@RequiredArgsConstructor
@Slf4j
public class AccountBookController {

    private final AccountBookService service;

    // 수입/지출 내역 리스트 조회
    @GetMapping("")
    public ResponseEntity<ApiResponse<AccountBooksResponse>> getAccountBooks(AccountBookGetRequest request) {
        // 페이징 파라미터 검증
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);

        // 페이징 요청 생성
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), request.getMemberId());

        // 거래 내역 조회
        List<AccountBook> accountBooks = service.getAccountBooks(pageRequest, request.getMemberId());

        // 거래 내역 총 개수 조회
        int totalCount = service.getTotalAccountBooksCount(request.getMemberId());

        // 응답 객체 생성
        AccountBooksResponse response = AccountBooksResponse.builder()
                .accountBooks(accountBooks)
                .totalCount(totalCount)
                .build();

        // 200 OK 응답 반환
        return ResponseEntity.ok(ApiResponse.of(response));
    }



    // 수입/지출 개별 내역 조회
    @GetMapping("/{accountBookId}")
    public ApiResponse<?> getAccountBook(@PathVariable long accountBookId, @RequestParam int memberId) {
        AccountBook accountBook = service.getAccountBook(accountBookId, memberId);
        return ApiResponse.of(AccountBookItem.of(accountBook));
    }

    // 수입/지출 내역 등록
    @PostMapping("")
    public ApiResponse<?> create(@RequestBody AccountBookCreateRequest request) {
        AccountBookCreateParam param = request.convert();
        service.createAccountBook(param.toEntity());
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 수정
    @PatchMapping("/{accountBookId}")
    public ApiResponse<?> update(@PathVariable long accountBookId, @RequestBody AccountBookUpdateRequest request, @RequestParam int memberId) {
        AccountBookUpdateParam param = request.convert();
        param.setAccountBookId(accountBookId);
        service.updateAccountBook(param, memberId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 삭제
    @DeleteMapping("/{accountBookId}")
    public ApiResponse<?> delete(@PathVariable long accountBookId, @RequestParam int memberId) {
        service.deleteAccountBook(accountBookId, memberId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    /* 페이징 정보 */
    @Data
    public static class AccountBookGetRequest {
        private int page = 0; // 현재 페이지 번호
        private int limit = 30; // 페이지당 항목 수
        private int memberId; // memberId 수정 (long -> int)
    }

    /* 내부 DTO 클래스 */
    @Data
    public static class AccountBookItem {
        private long accountBookId;
        private int memberId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date transactionDate;
        private String category;
        private int amount;
        private String type;
        private String description;
        private String paymentMethod;

        public static AccountBookItem of(AccountBook vo) {
            AccountBookItem item = new AccountBookItem();
            item.setAccountBookId(vo.getAccountBookId());
            item.setMemberId(vo.getMemberId());
            item.setTransactionDate(vo.getTransactionDate());
            item.setCategory(vo.getCategory());
            item.setAmount(vo.getAmount());
            item.setType(vo.getType());
            item.setDescription(vo.getDescription());
            item.setPaymentMethod(vo.getPaymentMethod());
            return item;
        }
    }

    @Data
    public static class AccountBookCreateRequest {
        private int memberId;
        private Date transactionDate;
        private String category;
        private int amount;
        private String type;
        private String description;
        private String paymentMethod;

        public AccountBookCreateParam convert() {
            return AccountBookCreateParam.builder()
                    .memberId(memberId)
                    .transactionDate(transactionDate)
                    .category(category)
                    .amount(amount)
                    .type(type)
                    .description(description)
                    .paymentMethod(paymentMethod)
                    .build();
        }
    }

    @Data
    public static class AccountBookUpdateRequest {
        private long accountBookId;
        private Date transactionDate;
        private String category;
        private int amount;
        private String type;
        private String description;
        private String paymentMethod;

        public AccountBookUpdateParam convert() {
            return AccountBookUpdateParam.builder()
                    .accountBookId(accountBookId)
                    .transactionDate(transactionDate)
                    .category(category)
                    .amount(amount)
                    .type(type)
                    .description(description)
                    .paymentMethod(paymentMethod)
                    .build();
        }
    }
}
