package com.example.spring_games.game_card.presentation;

import com.example.spring_games.game_card.application.GameCardService;
import com.example.spring_games.game_card.application.dto.GameCardDeleteRequest;
import com.example.spring_games.member.application.dto.GameCardRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameCardRestController {
    private final GameCardService gameCardService;

    @PostMapping("/game-cards")
    public void enrollGameCard(@Valid @RequestBody GameCardRequest gameCardRequest){
        gameCardService.enrollGameCard(gameCardRequest);
    }

    @DeleteMapping("/game-cards")
    public void deleteGameCard(@RequestBody GameCardDeleteRequest gameCardDeleteRequest){
        gameCardService.deleteCardByMemberIdAndCardId(gameCardDeleteRequest.gameCardId(), gameCardDeleteRequest.memberId());
    }
}
