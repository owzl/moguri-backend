package org.moguri.accountbook.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.accountbook.domain.AccountBook;

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

    public AccountBook toEntity() {
        return AccountBook.builder()
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
