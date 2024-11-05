package org.moguri.event.roulette.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.event.roulette.domain.Roulette;

import java.util.List;

@Mapper
public interface RouletteMapper {

    boolean hasPlayedRouletteToday(long memberId);

    void createRoulette(Roulette roulette);
}