package com.example.spring_games.game_card.presentation;

import com.example.spring_games.game.application.GameService;
import com.example.spring_games.game.application.dto.GameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameCardController {
    private final GameService gameService;

    @GetMapping("/members/{memberId}/game-card")
    public String getGameCardPage(@PathVariable Long memberId, Model model) {
        List<GameResponse> gameInfoList = gameService.getAllGameInfos();
        model.addAttribute("memberId", memberId);
        model.addAttribute("games", gameInfoList);
        return "enrollCard";
    }
}
