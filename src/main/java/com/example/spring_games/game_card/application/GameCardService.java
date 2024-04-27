package com.example.spring_games.game_card.application;

import com.example.spring_games.game.application.GameService;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.GameCardRepository;
import com.example.spring_games.member.application.MemberLevelNotificationService;
import com.example.spring_games.member.application.MemberService;
import com.example.spring_games.member.application.dto.GameCardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class GameCardService {
    private final GameCardRepository gameCardRepository;
    private final GameService gameService;

    public Long getGameCardCount(Long memberId) {
        return gameCardRepository.countGameCardByMemberId(memberId);
    }

    @Transactional
    public GameCard save(GameCardRequest gameCardRequest) {
        final Game game = gameService.findById(gameCardRequest.gameId());
        final Long serialNumber = game.makeSerialNumber();
        final GameCard gameCard = gameCardRequest.toEntity(game, serialNumber);
        return gameCardRepository.save(gameCard);
    }

    public List<GameCard> findAllByMemberId(Long memberId) {
        return gameCardRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public void deleteAllByMemberId(Long memberId){
        gameCardRepository.deleteAllByMemberId(memberId);
    }
    @Transactional
    public void deleteCardByMemberIdAndCardId(Long gameCardId, Long memberId){
        gameCardRepository.deleteGameCardByIdAndMemberId(gameCardId, memberId);
    }



    public List<GameCard> getMembersCardList(Long memberId) {
        return gameCardRepository.findAllByMemberId(memberId);
    }
}