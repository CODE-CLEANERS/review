package com.example.spring_games.common.test_instance;

import com.example.spring_games.common.repository.Repositories;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EntityProvider {
    @Autowired
    private Repositories repositories;

    public Game saveGame(Game game){
        return repositories.getGameRepository().saveAndFlush(game);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Game saveGameOnSeperatedTransaction(Game game){
        return repositories.getGameRepository().save(game);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Member saveMemberOnSeperatedTransaction(Member member){
        return repositories.getMemberRepository().save(member);
    }

    public GameCard saveGameCard(GameCard gameCard){
        return repositories.getGameCardRepository().save(gameCard);
    }

    public Member saveMember(Member member){
        return repositories.getMemberRepository().save(member);
    }
}
