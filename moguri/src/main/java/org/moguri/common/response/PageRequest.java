package org.moguri.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRequest {
    private int page; // 요청 페이지
    private int limit; // 한 페이지 당 데이터 건수

    public static PageRequest of(int page, int limit) {
        return new PageRequest(page, limit);
    }

    public int getOffset() {
        return page * limit;
    }
}