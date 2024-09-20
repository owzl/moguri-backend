package org.moguri.accountbook.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.accountbook.domain.AccountBookVO;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountBookUpdateParam {
    private long accountBookId; // ID를 포함해야 함
    private Date transactionDate;
    private String category;
    private int amount;
    private String type;
    private String description;
    private String paymentMethod;

    public AccountBookVO toEntity() {
        return AccountBookVO.builder()
                .accountBookId(accountBookId)
                .transactionDate(transactionDate)
                .category(category)
                .amount(amount)
                .type(type)
                .description(description)
                .paymentMethod(paymentMethod)
                .build();
    }
}
