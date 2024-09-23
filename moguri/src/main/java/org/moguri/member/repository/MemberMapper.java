package org.moguri.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.member.domain.Member;
import org.moguri.member.param.MemberUpdateParam;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    List<Member> findAll(PageRequest pageRequest);

    int getTotalCount();

    int save(Member member);

    Optional<Member> findById(long id);

    void update(long id, MemberUpdateParam param);

    void delete(long id);

}