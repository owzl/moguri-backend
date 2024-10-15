package org.moguri.accountbook.repository;

import org.apache.ibatis.annotations.Param;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.common.response.PageRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface AccountBookMapper {

    /* === 수입/지출 관리 === */
    // 수입/지출 내역 리스트 조회
      List<AccountBook> getAccountBooks(@Param("pageRequest") PageRequest pageRequest, @Param("memberId") long memberId);
    // 수입/지출 내역 개수 - 페이징
      int getAccountBooksCount(@Param("memberId") long memberId);

    // 수입/지출 개별 내역 조회
    AccountBook getAccountBook(long accountBookId);

    // 수입/지출 내역 작성
    void createAccountBook(AccountBook accountBook);

    // 수입/지출 내역 수정
    int updateAccountBook(AccountBook accountBook);

    // 수입/지출 내역 삭제
    int deleteAccountBook(long accountBookId);

    /* === 목표와 연동 === */
    // 지출 목표 - currentAmount 구하기
    // BigDecimal getCurrentAmountForCategory(String category, Date startDate, Date endDate);
    BigDecimal getCurrentAmountForCategory(Map<String, Object> params);

    // 저축 목표 - currentAmount 구하기
    BigDecimal getCurrentAmountForDescription(String description);

    // goalAmount 구하기
     BigDecimal getGoalAmountForCategory(long questId);

}
