package com.example.spring_games.member.domain;

import com.example.spring_games.member.application.dto.MemberInfoRequest;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {

    private static Member TEST_MEMBER;

    @BeforeEach
    void setUp() {
        TEST_MEMBER = new Member(
                new MemberName("TEST"),
                new MemberEmail("TEST@naver.com"),
                new MemberJoinDate("2024-01-01")
        );
    }

    @Test
    void updateMemberInfo() {

        MemberInfoRequest updateRequest = new MemberInfoRequest(
                "TESTT",
                "TEST2@naver.com",
                "2024-01-02"
        );

        TEST_MEMBER.updateMemberInfo(
                new MemberName(updateRequest.memberName()),
                new MemberEmail(updateRequest.memberEmail()),
                new MemberJoinDate(updateRequest.joinDate())
        );

        assertSoftly(
                softly -> {
                    softly.assertThat(TEST_MEMBER.getMemberName().getValue()).isEqualTo(updateRequest.memberName());
                    softly.assertThat(TEST_MEMBER.getEmail().getValue()).isEqualTo(updateRequest.memberEmail());
                    softly.assertThat(TEST_MEMBER.getJoinDate().getValue()).isEqualTo(updateRequest.joinDate());
                                            }
        );
    }

    @Test
    void testUpdateMemberLevel() {
        MemberLevel newLevel = MemberLevel.BRONZE;

        TEST_MEMBER.updateMemberLevel(newLevel);

        assertEquals(newLevel, TEST_MEMBER.getMemberLevel());
    }
}