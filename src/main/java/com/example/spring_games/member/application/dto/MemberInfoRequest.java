package com.example.spring_games.member.application.dto;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberInfoRequest (
        @NotBlank(message = "회원 이름을 입력 해 주세요.")
        @Size(min = 2, max = 100, message = "회원 이름은 2자 이상 100자 이하이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "회원 이름은 한글 혹은 영문만 가능합니다.")
        String memberName,

        @NotBlank(message = "이메일을 입력 해 주세요.")
        @Pattern(regexp =  "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "올바른 이메일 형식이어야 합니다.")
        String memberEmail,

        @NotBlank(message = "가입 날짜를 입력 해 주세요.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "가입 날짜 형식은 yyyy-MM-dd이어야 합니다")
        String joinDate
) {
    public Member toEntity(){
        return new Member(new MemberName(memberName), new MemberEmail(memberEmail), new MemberJoinDate(joinDate));
    }
}