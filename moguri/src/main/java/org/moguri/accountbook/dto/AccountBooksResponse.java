package org.moguri.accountbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.moguri.accountbook.domain.AccountBook;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AccountBooksResponse {
    private List<AccountBook> accountBooks; // 거래 내역 리스트
    private int totalCount; // 총 개수
}