package org.moguri.common.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoguriPage<T> {
    private List<T> contents; // 데이터 목록
    private int totalCount; // 전체 데이터 건수
    private int totalPage; // 전체 페이지 수
    private PageRequest pageRequest;

    public static <T> MoguriPage of(PageRequest pageRequest, int totalCount, List<T> list) {
        int totalPage = (int) Math.ceil((double) totalCount / pageRequest.getLimit()); // 전체 페이지 수 계산
        return new MoguriPage(list, totalCount, totalPage, pageRequest);
    }

    public int getPageNum() {
        return pageRequest.getPage();
    }

    public int getAmount() {
        return pageRequest.getLimit();
    }
}

