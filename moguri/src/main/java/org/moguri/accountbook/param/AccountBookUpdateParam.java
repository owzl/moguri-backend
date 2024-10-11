package org.moguri.accountbook.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moguri.accountbook.domain.AccountBook;

import java.util.Date;



@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountBookUpdateParam {
    private long accountBookId;
    private Date transactionDate;
    private String category;
    private int amount;
    private String type;
    private String description;
    private String paymentMethod;

    public AccountBook toEntity() {
        return AccountBook.builder()
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
