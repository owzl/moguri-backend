package org.moguri.event.roulette.service;

import org.moguri.common.response.PageRequest;
import org.moguri.event.roulette.domain.Roulette;

import java.util.List;

public interface RouletteService {

    boolean hasPlayedRouletteToday(long memberId);

    void createRoulette(Roulette roulette);
}
