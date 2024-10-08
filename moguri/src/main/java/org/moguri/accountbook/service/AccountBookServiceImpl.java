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

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookMapper accountBookMapper;

    @Override
    public List<AccountBook> getAccountBooks(PageRequest pageRequest, long memberId) {
        log.info("Fetching account books for memberId: {}", memberId);
        return accountBookMapper.getAccountBooks(pageRequest, memberId);
    }

    @Override
    public int getTotalAccountBooksCount(long memberId) {
        log.info("Fetching account book count for memberId: {}", memberId);
        return accountBookMapper.getAccountBooksCount(memberId);
    }

    @Override
    public AccountBook getAccountBook(long accountBookId, long memberId) {
        AccountBook accountBook = Optional.ofNullable(accountBookMapper.getAccountBook(accountBookId, memberId))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return accountBook;
    }

    @Override
    public void createAccountBook(AccountBook accountBook) {
        accountBookMapper.createAccountBook(accountBook);
    }

    @Override
    public void updateAccountBook(AccountBookUpdateParam param, long memberId) {
        accountBookMapper.updateAccountBook(param.toEntity());
    }

    @Override
    public void deleteAccountBook(long accountBookId, long memberId) {
        accountBookMapper.deleteAccountBook(accountBookId, memberId);
    }
}