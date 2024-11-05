package org.moguri.moguri.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.exception.MoguriRequestException;
import org.moguri.member.service.MemberService;
import org.moguri.moguri.domain.Moguri;
import org.moguri.moguri.repository.MoguriMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MoguriService {

    private final MoguriMapper moguriMapper;

    private final MemberService memberService;

    public List<Moguri> getAllMoguri(PageRequest pageRequest) {
        return moguriMapper.findAllMoguri(pageRequest);
    }

    public List<Moguri> getAllUserMoguri(PageRequest pageRequest, Long memberId) {
        return moguriMapper.findAllUserMoguri(pageRequest, memberId);
    }

    public Moguri getMoguri(Long moguriId) {
        return moguriMapper.findById(moguriId);
    }

    public void purchaseMoguri(Long moguriId, Long memberId) {
        int cottonCandy = memberService.getCottonCandy(memberId);
        int moguriPrice = getMoguri(moguriId).getMoguriPrice();
        if (cottonCandy < moguriPrice) {
            throw new MoguriRequestException(ReturnCode.NOT_ENOUGH_COTTON_CANDY);
        }
        memberService.updateCottonCandy(memberId, cottonCandy - moguriPrice);
        moguriMapper.purchaseMoguri(moguriId, memberId);
    }
}
