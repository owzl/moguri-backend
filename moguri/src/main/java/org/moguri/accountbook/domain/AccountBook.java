package org.moguri.accountbook.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBook {
    private long accountBookId; // Auto_Increment
    private long memberId; // 외래키
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date transactionDate; // 거래 날짜
    private String category; // 카테고리
    private int amount; // 금액
    private String type; // 거래 유형 (수입, 지출)
    private String description; // 설명
    private String paymentMethod; // 결제 방법
    private Date createdAt; // 생성 날짜
    private Date updatedAt; // 업데이트 날짜
    private Date deletedAt; // 삭제 날짜
}
