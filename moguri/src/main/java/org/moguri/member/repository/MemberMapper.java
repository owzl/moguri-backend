package org.moguri.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.member.domain.Member;
import org.moguri.member.param.MemberUpdateParam;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> findAll(PageRequest pageRequest);

    int getTotalCount();

    int save(Member member);

    Member findById(long id);

    void update(long id, MemberUpdateParam param);

    void delete(long id);

}