package org.moguri.accountbook.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookCreateParam;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.service.AccountBookService;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.exception.MoguriLogicException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/accountbook")
@RequiredArgsConstructor
@Slf4j
public class AccountBookController {

    private final AccountBookService service;

    // 수입/지출 내역 리스트 조회
    @GetMapping("")
    public ApiResponse<?> getAccountBooks() {
        List<AccountBook> accountBooks = service.getAccountBooks();
        List<AccountBookItem> items = accountBooks.stream()
                .map(AccountBookItem::of)
                .collect(Collectors.toList());
        return ApiResponse.of(items);
    }

    // 수입/지출 개별 내역 조회
    /*@GetMapping("/{accountBookId}")
    public ApiResponse<AccountBookItem> getAccountBook(@PathVariable long accountBookId) {
        AccountBook accountBook = service.getAccountBook(accountBookId);
        return ApiResponse.of(AccountBookItem.of(accountBook));
    }*/
    @GetMapping("/{accountBookId}")
    public ApiResponse<?> getAccountBook(@PathVariable long accountBookId) {
        Optional<AccountBook> optionalAccountBook = service.getAccountBook(accountBookId);

        // 예외 처리
        AccountBook accountBook = optionalAccountBook.orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

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
    public ApiResponse<?> update(@PathVariable long accountBookId, @RequestBody AccountBookUpdateRequest request) {
        AccountBookUpdateParam param = request.convert();
        service.updateAccountBook(accountBookId, param);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    // 수입/지출 내역 삭제
    @DeleteMapping("/{accountBookId}")
    public ApiResponse<?> delete(@PathVariable long accountBookId) {
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
