package org.moguri.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.moguri.accountbook.domain.AccountBook;
import org.moguri.accountbook.param.AccountBookUpdateParam;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookMapper accountBookMapper;

    @Override
    public List<AccountBook> getAccountBooks(PageRequest pageRequest, int memberId) { // memberId의 타입 변경
        List<AccountBook> res = null;
        try {
            log.info("멤버아이디: {}", memberId);
            res = accountBookMapper.getAccountBooks(pageRequest);
        } catch (Exception e) {
            log.info("에러 :::::::::::::::: {}", e.getMessage());
        }
        log.info("res ::::::::::::: {}", res);
        return res;
    }

    @Override
    public int getTotalAccountBooksCount(int memberId) { // memberId의 타입 변경
        log.info("Fetching account book count for memberId: {}", memberId);
        return accountBookMapper.getAccountBooksCount(memberId);
    }

    @Override
    public AccountBook getAccountBook(long accountBookId, int memberId) { // memberId의 타입 변경
        AccountBook accountBook = Optional.ofNullable(accountBookMapper.getAccountBook(accountBookId, memberId))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return accountBook;
    }

    @Override
    public void createAccountBook(AccountBook accountBook) {
        accountBookMapper.createAccountBook(accountBook);
    }

    @Override
    public void updateAccountBook(AccountBookUpdateParam param, int memberId) { // memberId의 타입 변경
        accountBookMapper.updateAccountBook(param.toEntity());
    }

    @Override
    public void deleteAccountBook(@Param("accountBookId")long accountBookId, @Param("memberId") int memberId) { // memberId의 타입 변경
        accountBookMapper.deleteAccountBook(accountBookId, memberId);
    }
}
