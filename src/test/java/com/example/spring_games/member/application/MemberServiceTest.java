package com.example.spring_games.member.application;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.common.test_instance.MemberFixture;
import com.example.spring_games.member.application.dto.MemberInfoRequest;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;


    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입에 성공한다.")
    void testSignup() {
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest(
                "TEST",
                "TEST@email.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        Long memberId = memberService.signup(memberInfoRequest);

        assertThat(memberId).isNotNull();
    }

    @Test
    @DisplayName("회원 정보 변경에 성공한다.")
    void testUpdateMemberInfo() {
        // given
        Member member = entityProvider.saveMember(MemberFixture.TEST);
        Long memberId = member.getId();
        MemberInfoRequest updateRequest = new MemberInfoRequest(
                "TestUser",
                "TEST2@email.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        // when
        Long updatedMemberId = memberService.updateMemberInfo(memberId, updateRequest);
        assertThat(memberId).isEqualTo(updatedMemberId);

        Member foundMember = memberRepository.findById(updatedMemberId).orElse(null);
        assertThat(foundMember).isNotNull();

        assertSoftly(
                softly -> {
                    softly.assertThat(foundMember.getMemberName().getValue()).isEqualTo(updateRequest.memberName());
                    softly.assertThat(foundMember.getEmail().getValue()).isEqualTo(updateRequest.memberEmail());
                    softly.assertThat(foundMember.getJoinDate().getValue()).isEqualTo(updateRequest.joinDate());
                }
        );
    }

    @Test
    @DisplayName("회원의 아이디로 회원 조회에 성공한다")
    void testFindById() {
        Member member = entityProvider.saveMember(MemberFixture.TEST);
        Long memberId = member.getId();

        Member foundMember = memberService.findById(memberId);

        assertNotNull(foundMember);
        assertThat(member).isEqualTo(foundMember);
    }

    @Test
    @DisplayName("회원 이름, 회원 등급으로 회원 리스트 조회에 성공한다")
    void testFindAllByMemberNameValueAndMemberLevel() {
        // given
        Member member1 = entityProvider.saveMember(MemberFixture.TEST);
        Member member2 = entityProvider.saveMember(MemberFixture.MEMBER_DK);
        MemberLevel memberLevel = MemberLevel.BRONZE;

        // when
        List<Member> members = memberService.findAllByMemberNameValueAndMemberLevel(member1.getMemberName().getValue(), memberLevel);

        // then
        assertThat(members).containsExactly(member1);
    }
}