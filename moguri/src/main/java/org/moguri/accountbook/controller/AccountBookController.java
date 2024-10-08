package org.moguri.accountbook.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookCreateParam;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.service.AccountBookService;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
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
    public ApiResponse<?> getAccountBooks(AccountBookGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), request.getMemberId());

        // memberId를 함께 전달하여 해당 사용자의 거래 내역만 조회
        List<AccountBook> accountBooks = service.getAccountBooks(pageRequest, request.getMemberId());
        int totalCount = service.getTotalAccountBooksCount(request.getMemberId()); // memberId 기준 총 개수

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                accountBooks.stream().map(AccountBookController.AccountBookItem::of).toList()));
    }

    // 수입/지출 개별 내역 조회
    @GetMapping("/{accountBookId}")
    public ApiResponse<?> getAccountBook(@PathVariable long accountBookId, @RequestParam long memberId) {
        // memberId와 accountBookId를 함께 확인하여 개별 내역 조회
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
    public ApiResponse<?> update(@PathVariable long accountBookId, @RequestBody AccountBookUpdateRequest request, @RequestParam long memberId) {
        AccountBookUpdateParam param = request.convert();
        param.setAccountBookId(accountBookId); // PathVariable로 받은 accountBookId 설정
        // memberId도 설정할 수 있습니다.
        service.updateAccountBook(param, memberId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 삭제
    @DeleteMapping("/{accountBookId}")
    public ApiResponse<?> delete(@PathVariable long accountBookId, @RequestParam long memberId) {
        service.deleteAccountBook(accountBookId, memberId); // 삭제 시에도 memberId를 전달
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    /* 페이징 정보 */
    @Data
    public static class AccountBookGetRequest {
        private int page = 0; // 현재 페이지 번호
        private int limit = 30; // 페이지당 항목 수
        private long memberId; // memberId 추가
    }

    /* 내부 DTO 클래스 */
    @Data
    public static class AccountBookItem {
        private long accountBookId;
        private long memberId;
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
        private long memberId;
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