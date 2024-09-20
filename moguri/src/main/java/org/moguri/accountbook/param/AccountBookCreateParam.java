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
public class AccountBookCreateParam {
    private long memberId;
    private Date transactionDate;
    private String category;
    private int amount;
    private String type;
    private String description;
    private String paymentMethod;

    public AccountBookVO toEntity() {
        return AccountBookVO.builder()
                .memberId(memberId)
                .transactionDate(transactionDate)
                .category(category)
                .amount(amount)
                .type(type)
                .description(description)
                .paymentMethod(paymentMethod)
                .build();
    }
}
