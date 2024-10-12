package org.moguri.moguri.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.moguri.domain.Moguri;

import java.util.List;

@Mapper
public interface MoguriMapper {
    List<Moguri> findAllMoguri(@Param("pageRequest") PageRequest pageRequest);

    List<Moguri> findAllUserMoguri(@Param("pageRequest") PageRequest pageRequest, @Param("memberId") Long memberId);

    void purchaseMoguri(@Param("moguriId") Long moguriId, @Param("memberId") Long memberId);

    Moguri findById(@Param("moguriId") Long moguriId);
}
