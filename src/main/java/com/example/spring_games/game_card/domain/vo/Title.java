package com.example.spring_games.game_card.domain.vo;

import com.example.spring_games.game_card.exception.GameCardException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Title {
    private static final int MAXIMUM_LENGTH = 100;

    @Column(name = "title")
    private String value;

    public Title(final String input) {
        validateNull(input);
        final String trimmedInput = input.trim();
        validateLength(trimmedInput);
        this.value = trimmedInput;
    }

    private static void validateNull(final String input){
        if (Objects.isNull(input)){
            throw new NullPointerException("게임카드의 타이틀은 null일 수 없습니다.");
        }
    }

    private static void validateLength(final String input){
        if (input.isEmpty() || input.length() > MAXIMUM_LENGTH){
            throw new GameCardException.InvalidTitleException(input);
        }
    }
}
