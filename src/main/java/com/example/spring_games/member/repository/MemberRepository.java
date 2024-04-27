package com.example.spring_games.member.repository;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.repository.query.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {
    boolean existsByEmailValue(String email);
}
