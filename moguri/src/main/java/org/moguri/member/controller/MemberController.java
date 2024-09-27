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

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ApiResponse<?> create(@RequestBody MemberCreateRequest request) {
        MemberCreateParam param = request.convert();
        memberService.saveMember(param);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @GetMapping("/{id}")   // 회원 정보를 ID로 조회
    public ApiResponse<?> getMember(@PathVariable("id") Long id) {
        Member member = memberService.getMember(id);
        return ApiResponse.of(MemberItem.of(member));
    }

    @DeleteMapping("/{id}")  // 회원 삭제 서비스를 호출
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id); // 삭제 메서드 호출
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @PatchMapping("/{id}") //  특정 id 회원 정보 업데이트
    public ApiResponse<?> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest request) {
        MemberUpdateParam param = request.convert();
        memberService.updateMember(id, param); // 업데이트 메서드 호출
        return ApiResponse.of(ReturnCode.SUCCESS);
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
            return MemberCreateParam.builder()
                    .email(email)
                    .password(password)
                    .nickName(nickName)
                    .build();
        }
    }

    @Data
    private static class MemberUpdateRequest {
        private String password; // 비밀번호 업데이트
        private String nickName; // 닉네임 업데이트

        public MemberUpdateParam convert() {
            return MemberUpdateParam.builder()
                    .password(password) // 비밀번호 포함
                    .nickName(nickName) // 닉네임 포함
                    .build();
        }
    }
}
