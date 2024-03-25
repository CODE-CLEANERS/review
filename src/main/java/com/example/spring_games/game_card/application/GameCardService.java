package com.example.spring_games.game_card.application;

import com.example.spring_games.game.application.GameService;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.GameCardRepository;
import com.example.spring_games.game_card.domain.vo.Cards;
import com.example.spring_games.member.application.MemberLevelNotificationService;
import com.example.spring_games.member.application.MemberService;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberLevel;
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
    private final MemberService memberService;
    private final GameService gameService;
    private final MemberLevelNotificationService memberLevelNotificationService;

    public Long getGameCardCount(Long memberId) {
        return gameCardRepository.countGameCardByMemberId(memberId);
    }

    @Transactional
    public void enrollGameCard(GameCardRequest gameCardRequest) {
        final Member member = memberService.findById(gameCardRequest.memberId());
        final GameCard gameCard = this.save(gameCardRequest);
        updateMemberLevelWithNewCard(member, gameCard);
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

    private GameCard save(GameCardRequest gameCardRequest) {
        final Game game = gameService.findById(gameCardRequest.gameId());
        final Long serialNumber = game.makeSerialNumber();
        final Member member = memberService.findById(gameCardRequest.memberId());
        final GameCard gameCard = gameCardRequest.toEntity(game, serialNumber, member);
        return gameCardRepository.save(gameCard);
    }

    private void updateMemberLevelWithNewCard(Member member, GameCard newCard) {
        Cards memberGameCards = getAllGameCardsForMember(member.getId(), newCard);

        MemberLevel prevLevel = member.getMemberLevel();
        MemberLevel updatedLevel = MemberLevel.calculateMemberLevel(memberGameCards);

        if (prevLevel.isNotEqual(updatedLevel)) {
            member.updateMemberLevel(updatedLevel);
            memberLevelNotificationService.notifyMemberLevelChanged(member.getId(), member.getMemberName(), updatedLevel);
        }
    }

    private Cards getAllGameCardsForMember(Long memberId, GameCard gameCard) {
        List<GameCard> allGameCards = gameCardRepository.findAllByMemberId(memberId);
        allGameCards.add(gameCard);
        return new Cards(allGameCards);
    }
}