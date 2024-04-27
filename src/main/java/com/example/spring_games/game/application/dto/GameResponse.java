package com.example.spring_games.game.application.dto;

import com.example.spring_games.game.domain.Game;

public record GameResponse(Long gameId, String gameName) {
    public static GameResponse of(Game game){
        return new GameResponse(game.getId(), game.getGameName().getValue());
    }
}