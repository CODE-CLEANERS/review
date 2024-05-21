package com.example.spring_games.member.domain;

public interface MemberRepository {
    boolean existsByEmailValue(String email);
}


// TODO : 도메인 모델 패턴 더 알아보기 + // USECASE 예제
