package org.moguri.security.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
// 사용자 정보 관리 (사용자 정보의 전체 관리)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberVO {
    private long memberId; // MEMBER_ID를 PK로 사용
    private String email; // 이메일
    private String nickName; // 닉네임
    private String password; // 비밀번호
    private Date createdAt; // 생성일
    private Date updatedAt; // 수정일
    private Date deletedAt; // 삭제일

    private List<AuthVO> authList; // 권한 정보
}
