package com.example.spring_games.member.domain;

import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberName memberName;

    @Embedded
    private MemberEmail email;

    @Embedded
    private MemberJoinDate joinDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_level")
    private MemberLevel memberLevel;

    public Member(MemberName memberName, MemberEmail email, MemberJoinDate memberJoinDate) {
        this.memberName = memberName;
        this.email = email;
        this.joinDate = memberJoinDate;
        this.memberLevel = MemberLevel.BRONZE;
    }

    public void updateMemberInfo(MemberName memberName, MemberEmail email, MemberJoinDate memberJoinDate){
        this.memberName = memberName;
        this.email = email;
        this.joinDate = memberJoinDate;
    }

    public void updateMemberLevel(MemberLevel memberLevel){
        this.memberLevel = memberLevel;
    }
}