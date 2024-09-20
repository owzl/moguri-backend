package org.moguri.accountbook.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBookVO;
import org.moguri.accountbook.param.AccountBookCreateParam;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.service.AccountBookService;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/accountbook")
@RequiredArgsConstructor
@Slf4j
public class AccountBookController {

    private final AccountBookService service;

    // 수입/지출 내역 리스트 조회
    @GetMapping("")
    public ApiResponse<List<AccountBookItem>> getAccountBookList() {
        List<AccountBookVO> accountBooks = service.getAccountBookList();
        List<AccountBookItem> items = accountBooks.stream()
                .map(AccountBookItem::of)
                .collect(Collectors.toList());
        return ApiResponse.of(items);
    }

    // 수입/지출 개별 내역 조회
    @GetMapping("/{accountBookId}")
    public ApiResponse<AccountBookItem> getAccountBook(@PathVariable long accountBookId) {
        AccountBookVO accountBookVO = service.getAccountBook(accountBookId);
        return ApiResponse.of(AccountBookItem.of(accountBookVO));
    }

    // 수입/지출 내역 등록
    @PostMapping
    public ApiResponse<ReturnCode> create(@RequestBody AccountBookCreateRequest request) {
        AccountBookCreateParam param = request.convert();
        service.createAccountBook(param.toEntity());
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 수정
    @PatchMapping("/{accountBookId}")
    public ApiResponse<ReturnCode> update(@PathVariable long accountBookId, @RequestBody AccountBookUpdateRequest request) {
        request.setAccountBookId(accountBookId); // ID 설정
        AccountBookUpdateParam param = request.convert();
        service.updateAccountBook(param.toEntity());
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 삭제
    @DeleteMapping("/{accountBookId}")
    public ApiResponse<ReturnCode> delete(@PathVariable long accountBookId) {
        service.deleteAccountBook(accountBookId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }


    // 내부 DTO 클래스
    @Data
    public static class AccountBookItem {
        private long accountBookId;
        private long memberId;
        private Date transactionDate;
        private String category;
        private int amount;
        private String type;
        private String description;
        private String paymentMethod;

        public static AccountBookItem of(AccountBookVO vo) {
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
        private long accountBookId; // ID 필드 추가
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
