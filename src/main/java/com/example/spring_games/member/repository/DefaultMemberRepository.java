package com.example.spring_games.member.repository;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.MemberRepository;
import com.example.spring_games.member.repository.query.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultMemberRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean existsByEmailValue(String email) {

    }
}
