package org.moguri.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookMapper accountBookMapper;

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
            accountBookMapper.createAccountBook(accountBook);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    // 수입/지출 내역 수정
    @Override
    public void updateAccountBook(AccountBookUpdateParam param) {
        try {
            accountBookMapper.updateAccountBook(param.toEntity());
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    // 수입/지출 내역 삭제
    @Override
    public void deleteAccountBook(long accountBookId) {
        try {
            accountBookMapper.deleteAccountBook(accountBookId);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY);
        }
    }

}
