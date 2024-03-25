package com.example.spring_games.member.application.dto;

import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.vo.Price;
import com.example.spring_games.game_card.domain.vo.Title;
import com.example.spring_games.member.domain.Member;
import jakarta.validation.constraints.*;

public record GameCardRequest(
        @NotBlank(message = "타이틀을 입력해주세요.")
        @Size(max = 100, message = "타이틀은 100자 이하여야 합니다.") String title,
        @Positive(message = "가격은 0원 이상이어야 합니다.")
        @DecimalMax(value = "100000.0", message = "가격은 100000.0원을 넘을 수 없습니다.") double price,
        @NotNull(message = "회원 ID를 입력해주세요.") Long memberId,
        @NotNull(message = "게임 ID를 입력해주세요.") Long gameId)  {
    public GameCard toEntity(Game game, Long serialNumber, Member member) {
        return new GameCard(new Title(title), new Price(price), serialNumber, game, member);
    }
}