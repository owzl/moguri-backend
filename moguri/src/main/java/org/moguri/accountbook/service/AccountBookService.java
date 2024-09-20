package org.moguri.accountbook.service;

import org.moguri.accountbook.domain.AccountBookVO;

import java.util.List;

public interface AccountBookService {

    /* === 수입/지출 관리 === */

    // 모든 내역 조회
    List<AccountBookVO> getAccountBookList();

    // 수입/지출 개별 내역 조회
    AccountBookVO getAccountBook(long accountBookId);

    // 수입/지출 내역 등록
    void createAccountBook(AccountBookVO accountBook);

    // 수입/지출 내역 수정
    void updateAccountBook(AccountBookVO accountBook);

    // 수입/지출 내역 삭제
    void deleteAccountBook(long accountBookId);


    // 특정 날짜의 내역 조회 - maybe 캘린더용
    // List<AccountBookDTO> getAccountBookByDate(Date date);

}
