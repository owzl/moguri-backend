package org.moguri.accountbook.repository;

import org.apache.ibatis.annotations.Param;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.common.response.PageRequest;

import java.util.List;
import java.util.Map;


public interface AccountBookMapper {

    /* === 수입/지출 관리 === */
    // 수입/지출 내역 리스트 조회
    List<AccountBook> getAccountBooks(Map<String, Object> params);

    // 수입/지출 내역 개수 - 페이징
    int getAccountBooksCount(long memberId); // memberId 추가

    // 수입/지출 개별 내역 조회
    AccountBook getAccountBook(long accountBookId, long memberId); // memberId 추가

    // 수입/지출 내역 작성
    void createAccountBook(AccountBook accountBook);

    // 수입/지출 내역 수정
    int updateAccountBook(AccountBook accountBook);

    // 수입/지출 내역 삭제
    int deleteAccountBook(@Param("accountBookId") long accountBookId, @Param("memberId") long memberId); // memberId 추가



    // 특정 날짜의 내역 조회 - maybe 캘린더용
}