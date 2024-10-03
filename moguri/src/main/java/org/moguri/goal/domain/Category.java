package org.moguri.goal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private long categoryId;
    private String categoryName;
    private String categoryType;  // 시스템 / 사용자 구분
    private String transactionType;  // 수입 / 지출 구분
    private long memberId;  // 추후 카테고리 개인화 도입시 사용할 용도
}
