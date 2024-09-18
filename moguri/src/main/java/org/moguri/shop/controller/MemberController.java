package org.moguri.member.controller;

import lombok.RequiredArgsConstructor;

import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;

import lombok.Data;

import org.moguri.member.domain.Member;
import org.moguri.member.param.MemberCreateParam;
import org.moguri.member.param.MemberUpdateParam;
import org.moguri.member.service.MemberService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 페이징 처리 고민중 추후 구현
     */
//    @GetMapping
//    public ApiResponse<?> getMembers(MemberGetRequest request) {
//        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
//        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());
//
//        Stream<MemberItem> members = memberService.getMembers(pageRequest).stream().map(MemberItem::of);
//        members = members;
//        return ApiResponse.of(MoguriPage.of(members));
//    }
    @PostMapping
    public ApiResponse<?> create(@RequestBody MemberCreateRequest request) {
        MemberCreateParam param = request.convert();
        memberService.save(param);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getMember(@PathVariable("id") Long id) {
        Member member = memberService.getMember(id);
        return ApiResponse.of(MemberItem.of(member));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        //memberService.remove(loginMember, id);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest request) {
        MemberUpdateParam param = request.convert();
        //memberService.update(loginMember, id, param);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    private static class MemberGetRequest {

        private int page = 0;
        private int limit = 30;
        //default 값
    }

    @Data
    private static class MemberItem {

        private String email; // id

        private String password;

        private String nickName;

        private static MemberItem of(Member member) {
            MemberItem converted = new MemberItem();
            converted.email = member.getEmail();
            converted.password = member.getPassword();
            converted.nickName = member.getNickName();
            return converted;
        }
    }

    @Data
    private static class MemberCreateRequest {

        private String email; // id

        private String password;

        private String nickName;

        public MemberCreateParam convert() {
            MemberCreateParam param = MemberCreateParam.builder()
                    .email(email)
                    .password(password)
                    .nickName(nickName)
                    .build();
            return param;
        }
    }

    @Data
    private static class MemberUpdateRequest {

        private String password;

        private String nickName;

        public MemberUpdateParam convert() {
            MemberUpdateParam param = MemberUpdateParam.builder()
                    .password(password)
                    .nickName(nickName)
                    .build();
            return param;
        }
    }
}