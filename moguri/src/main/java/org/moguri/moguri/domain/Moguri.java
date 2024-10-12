package org.moguri.moguri.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Moguri {
    private Long moguriId;
    private String moguriName;
    private int moguriPrice;
    private String filePath;
}
