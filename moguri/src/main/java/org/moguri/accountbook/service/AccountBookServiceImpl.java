package org.moguri.accountbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moguri.accountbook.domain.AccountBookVO;
import org.moguri.accountbook.repository.AccountBookMapper;
import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookMapper mapper;

    @Override
    public List<AccountBookVO> getAccountBookList() {
        log.info("Fetching account book list...");
        return mapper.getAccountBookList();
    }

    @Override
    public AccountBookVO getAccountBook(long accountBookId) {
        log.info("Getting account book with ID: {}", accountBookId);
        return mapper.getAccountBook(accountBookId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }


    @Override
    public void createAccountBook(AccountBookVO accountBookVO) {
        try {
            mapper.createAccountBook(accountBookVO);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    @Override
    public void updateAccountBook(AccountBookVO accountBookVO) {
        try {
            mapper.updateAccountBook(accountBookVO);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    @Override
    public void deleteAccountBook(long accountBookId) {
        try {
            mapper.deleteAccountBook(accountBookId);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY);
        }
    }

}
