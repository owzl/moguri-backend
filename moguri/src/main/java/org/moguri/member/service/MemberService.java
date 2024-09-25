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
        return memberMapper.findById(id)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    public void remove(Long id) {
        memberMapper.findById(id)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        memberMapper.delete(id);
    }

    public void update(Long id, MemberUpdateParam param) {
        memberMapper.findById(id)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        memberMapper.update(id, param);
    }
}
