package org.moguri.member.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriLogicException;
import org.moguri.member.domain.Member;
import org.moguri.member.param.MemberCreateParam;
import org.moguri.member.param.MemberUpdateParam;
import org.moguri.member.repository.MemberMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Member> getMembers(PageRequest pageRequest) {
        return memberMapper.findAll(pageRequest);
    }

    // 회원 정보 저장
    public void saveMember(MemberCreateParam createParam) {
        Member member = createParam.toEntity();
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);
        memberMapper.insert(member);
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberMapper.findById(memberId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    // 회원 정보 업데이트
    public void updateMember(Long memberId, MemberUpdateParam updateParam) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

        if (updateParam.getPassword() != null && !updateParam.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(updateParam.getPassword());
            member.setPassword(hashedPassword);
        }

        if (updateParam.getNickName() != null && !updateParam.getNickName().isEmpty()) {
            member.setNickName(updateParam.getNickName());
        }

        memberMapper.update(member);
    }

    // 회원 삭제
    public void deleteMember(Long memberId) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

        memberMapper.delete(member);
    }
}
