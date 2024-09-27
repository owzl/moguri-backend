package org.moguri.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.member.domain.Member;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 회원 정보 추가
    int insert(Member member);

    // ID로 회원 조회
    Optional<Member> findById(long id);

    // 이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    // 회원 정보 수정
    int update(Member member);
    // 회원 삭제 메서드
    void delete(Member member);
    // 모든 회원 조회 (페이지네이션)
    List<Member> findAll(PageRequest pageRequest);
}
