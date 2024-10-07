package org.moguri.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.moguri.goal.domain.Goal;
import org.moguri.goal.param.GoalUpdateParam;
import org.moguri.goal.repository.GoalMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookMapper accountBookMapper;
    private final GoalMapper goalMapper;

    // 수입/지출 내역 리스트 조회
    @Override
    public List<AccountBook> getAccountBooks(PageRequest pageRequest) {
        log.info("Fetching account book list...");
        return accountBookMapper.getAccountBooks(pageRequest);
    }

    // 수입/지출 내역 개수 - 페이징
    @Override
    public int getTotalAccountBooksCount() {
        return accountBookMapper.getAccountBooksCount();
    }

    // 수입/지출 개별 내역 조회
    @Override
    public AccountBook getAccountBook(long accountBookId) {
        AccountBook accountBook = Optional.ofNullable(accountBookMapper.getAccountBook(accountBookId)).orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return accountBook;
    }

    // 수입/지출 내역 등록
    @Override
    public void createAccountBook(AccountBook accountBook) {

        try {
            // 가계부 항목 생성
            accountBookMapper.createAccountBook(accountBook);

            // 목표 조회
            Goal goal = goalMapper.findByGoalCategory(accountBook.getCategory());

            if (goal != null) {
                // 저축 목표 - currentAmount 업데이트 (조건 추가)
                if (accountBook.getType().equals("저축")) { // 타입이 '저축'일 경우에만 호출
                    updateCurrentAmountByDescription(accountBook.getDescription());
                }else{
                    // 지출 목표 - currentAmount 업데이트
                    getCurrentAmountForCategory(accountBook.getCategory(), goal);
                }
            }

        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    // 수입/지출 내역 수정
    @Override
    public void updateAccountBook(AccountBookUpdateParam param) {
        try {
            // 가계부 항목 수정
            accountBookMapper.updateAccountBook(param.toEntity());

            // 목표 조회
            Goal goal = goalMapper.findByGoalCategory(param.getCategory());

            // 저축 목표 - currentAmount 업데이트 (조건 추가)
            if (param.getType().equals("저축")) {
                updateCurrentAmountByDescription(param.getDescription());
            } else if (goal != null) {
                // 지출 목표 - currentAmount 업데이트
                getCurrentAmountForCategory(param.getCategory(), goal);
            }

        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }


    // 수입/지출 내역 삭제
    @Override
    public void deleteAccountBook(long accountBookId) {
        try {
            // 삭제할 가계부 항목 조회
            AccountBook accountBook = accountBookMapper.getAccountBook(accountBookId);

            if (accountBook != null) {
                // 가계부 항목 삭제
                accountBookMapper.deleteAccountBook(accountBookId);

                // 목표 조회
                Goal goal = goalMapper.findByGoalCategory(accountBook.getCategory());

                // 저축 목표 - currentAmount 업데이트
                if (accountBook.getType().equals("저축")) {
                    updateCurrentAmountByDescription(accountBook.getDescription());
                } else if (goal != null) {
                    // 지출 목표 - currentAmount 업데이트
                    getCurrentAmountForCategory(accountBook.getCategory(), goal);
                }
            } else {
                throw new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY);
            }
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY);
        }
    }



    /* === 목표와 연동 === */
    // 카테고리 이용하여 currentAmount 업데이트 메서드
    private void getCurrentAmountForCategory(String category, Goal goal) {
        // 목표의 startDate와 endDate 가져오기
        Date startDate = goal.getStartDate();
        Date endDate = goal.getEndDate();

        // 해당 카테고리의 currentAmount 계산
        BigDecimal newCurrentAmount = accountBookMapper.getCurrentAmountForCategory(category, startDate, endDate);

        // Goal 객체의 currentAmount 업데이트
        goal.setCurrentAmount(newCurrentAmount);

        // Goal 객체를 업데이트하는 메서드 호출
        updateCurrentAmount(goal);
    }


    // 거래 상세 내역로 저축 목표 currentAmount 업데이트
    private void updateCurrentAmountByDescription(String description) {
        log.info("현재 저축 금액 업뎃: {}", description);

        // 해당 거래 상세 내역에 대한 currentAmount 계산
        BigDecimal newCurrentAmount = accountBookMapper.getCurrentAmountForDescription(description);

        // 목표 조회 (description을 기준으로)
        Goal goal = goalMapper.findByGoalName(description); // goal_name을 기준으로 목표 조회
        if (goal != null) {
            // 목표의 currentAmount 업데이트
            goal.setCurrentAmount(goal.getCurrentAmount().add(newCurrentAmount)); // 새로운 currentAmount 설정
            updateCurrentAmount(goal); // Goal 객체를 업데이트하는 메서드 호출
        }
    }

    // currentAmount 업데이트하는 본체 - Goal 객체를 받는 메서드
    private void updateCurrentAmount(Goal goal) {
        GoalUpdateParam updateParam = GoalUpdateParam.builder()
                .goalId(goal.getGoalId())
                .currentAmount(goal.getCurrentAmount())
                .build();

        goalMapper.updateCurrentAmount(updateParam);
    }
}
