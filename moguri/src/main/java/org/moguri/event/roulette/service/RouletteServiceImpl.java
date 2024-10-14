package org.moguri.event.roulette.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.event.roulette.domain.Roulette;
import org.moguri.event.roulette.repository.RouletteMapper;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RouletteServiceImpl implements RouletteService {

    private final RouletteMapper rouletteMapper;

    @Override
    public boolean hasPlayedRouletteToday(long memberId) {
        return rouletteMapper.hasPlayedRouletteToday(memberId);
    }

    @Override
    public void createRoulette(Roulette roulette) {
        try {
            rouletteMapper.createRoulette(roulette);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }
}
