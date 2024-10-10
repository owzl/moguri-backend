package org.moguri.accountbook.service;

import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.common.response.PageRequest;

import java.util.List;

public interface AccountBookService {

    // 수입/지출 모든 내역 조회
    List<AccountBook> getAccountBooks(PageRequest pageRequest, int memberId); // long -> int로 변경

    // 수입/지출 모든 내역 개수 - memberId 필터링 추가
    int getTotalAccountBooksCount(int memberId); // long -> int로 변경

    // 수입/지출 개별 내역 조회
    AccountBook getAccountBook(long accountBookId, int memberId); // long -> int로 변경

    // 수입/지출 내역 등록
    void createAccountBook(AccountBook accountBook);

    // 수입/지출 내역 수정
    void updateAccountBook(AccountBookUpdateParam param, int memberId); // long -> int로 변경

    // 수입/지출 내역 삭제
    void deleteAccountBook(long accountBookId, int memberId); // long -> int로 변경
}