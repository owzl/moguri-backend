package org.moguri.accountbook.service;

import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookUpdateParam;

import java.util.List;
import java.util.Optional;

public interface AccountBookService {

    /* === 수입/지출 관리 === */

    // 모든 내역 조회
    List<AccountBook> getAccountBooks();

    // 수입/지출 개별 내역 조회
    AccountBook getAccountBook(long accountBookId);

    // 수입/지출 내역 등록
    void createAccountBook(AccountBook accountBook);

    // 수입/지출 내역 수정
    void updateAccountBook(AccountBookUpdateParam param);

    // 수입/지출 내역 삭제
    void deleteAccountBook(long accountBookId);


    // 특정 날짜의 내역 조회 - maybe 캘린더용
    // List<AccountBookDTO> getAccountBookByDate(Date date);

}
