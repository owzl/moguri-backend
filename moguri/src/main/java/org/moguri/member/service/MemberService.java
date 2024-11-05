package org.moguri.member.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriLogicException;
import org.moguri.member.domain.Member;
import org.moguri.member.param.MemberCreateParam;
import org.moguri.member.param.MemberUpdateParam;
import org.moguri.member.repository.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    public List<Member> getMembers(PageRequest pageRequest) {
        return memberMapper.findAll(pageRequest);
    }

    public int getTotalCount() {
        return memberMapper.getTotalCount();
    }

    public void save(MemberCreateParam param) {
        Member member = param.toEntity();
        memberMapper.save(member);
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return Optional.ofNullable(memberMapper.findById(id))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    public void remove(Long id) {
        Optional.ofNullable(memberMapper.findById(id))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        memberMapper.delete(id);
    }

    public void update(MemberUpdateParam param) {
        Optional.ofNullable(memberMapper.findById(param.getId()))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        memberMapper.update(param); // param만 전달
    }
    // 코튼 캔디 업데이트 메소드 추가
    public void updateCottonCandy(Long id, int cottonCandy) {
        // 멤버가 존재하는지 확인
        Optional.ofNullable(memberMapper.findById(id))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));

        // 코튼 캔디 업데이트
        memberMapper.updateCottonCandy(id, cottonCandy);
    }
    public int getCottonCandy(Long id) {
        Member member = Optional.ofNullable(memberMapper.findById(id))
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return member.getCottonCandy();
    }
}
