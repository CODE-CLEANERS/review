package com.example.spring_games.member_card_facade.application.dto;

import com.example.spring_games.game_card.domain.GameCard;

public record CardDetailResponse(Long id,
                                 String game,
                                 String title,
                                 Long serialNumber,
                                 double price) {

    public static CardDetailResponse of(GameCard gameCard){
        return new CardDetailResponse(gameCard.getId(),
                gameCard.getGame().getGameName().getValue(),
                gameCard.getTitle().getValue(),
                gameCard.getSerialNumber(),
                gameCard.getPrice().getValue());
    }
}