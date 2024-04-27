package com.example.spring_games.common.repository;

import com.example.spring_games.game.domain.GameRepository;
import com.example.spring_games.game_card.domain.GameCardRepository;
import com.example.spring_games.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Repositories {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GameCardRepository gameCardRepository;

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public GameCardRepository getGameCardRepository() {
        return gameCardRepository;
    }
}
