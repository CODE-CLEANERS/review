package com.example.spring_games.game.application;

import com.example.spring_games.game.application.dto.GameResponse;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game.domain.GameRepository;
import com.example.spring_games.game.exception.GameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game findById(Long id){
        return gameRepository.findById(id)
                .orElseThrow(
                        () -> new GameException.GameNotFoundException(id)
                );
    }

    public List<GameResponse> getAllGameInfos(){
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .map(GameResponse::of)
                .toList();
    }
}
