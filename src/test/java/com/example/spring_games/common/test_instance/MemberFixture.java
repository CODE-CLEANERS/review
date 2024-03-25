package com.example.spring_games.common.test_instance;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberName;

public class MemberFixture {
    public static final Member TEST = new Member(
            new MemberName("TEST"),
            new MemberEmail("TEST@email.com"),
            new MemberJoinDate("2024-03-11")
    );

    public static final Member MEMBER_DK = new Member(
            new MemberName("김동균"),
            new MemberEmail("laancer4@gmail.com"),
            new MemberJoinDate("2024-03-18")
    );

    public static final Member TEST_MEMBER_FOR_SEPERATED_TRANSACTION = new Member(
            new MemberName("TESTMEMBER"),
            new MemberEmail("TESTTT@email.com"),
            new MemberJoinDate("2024-03-11")
    );
}
