package org.moguri.accountbook.repository;

import org.moguri.accountbook.domain.AccountBook;
import org.moguri.common.response.PageRequest;

import java.util.List;


public interface AccountBookMapper {

    /* === 수입/지출 관리 === */
    // 수입/지출 내역 리스트 조회
    List<AccountBook> getAccountBooks(PageRequest pageRequest); // 모든 내역 조회
    // 수입/지출 내역 개수 - 페이징
    int getAccountBooksCount();

    // 수입/지출 개별 내역 조회
    //AccountBookVO getAccountBook(long accountBookId);
    AccountBook getAccountBook(long accountBookId);

    // 수입/지출 내역 작성
    void createAccountBook(AccountBook accountBook);

    // 수입/지출 내역 수정
    int updateAccountBook(AccountBook accountBook);

    // 수입/지출 내역 삭제
    int deleteAccountBook(long accountBookId);

    // 특정 날짜의 내역 조회 - maybe 캘린더용
    // List<AccountBookVO> getAccountBookByDate(Date date);



}
