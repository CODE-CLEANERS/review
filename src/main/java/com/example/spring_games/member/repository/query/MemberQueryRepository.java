package com.example.spring_games.member.repository.query;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberLevel;

import java.util.List;

public interface MemberQueryRepository {
    List<Member> findAllByMemberNameValueAndMemberLevel(String memberNameValue, MemberLevel memberLevel);
}
