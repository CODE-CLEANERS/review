package com.example.spring_games.game.domain.vo;

import com.example.spring_games.game.exception.GameException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class GameName {
    private static final int MAXIMUM_LENGTH = 100;

    @Column(name = "name")
    private String value;

    public GameName(String input) {
        validateNull(input);
        String trimmedInput = input.trim();
        validate(trimmedInput);
        this.value = trimmedInput;
    }

    private static void validateNull(String input){
        if (input == null) {
            throw new NullPointerException("게임 이름은 null일 수 없습니다.");
        }
    }

    private static void validate(String trimmedInput){
        if (trimmedInput.isEmpty()){
            throw GameException.InvalidGameNameException.ofEmpty();
        }

        if (trimmedInput.length() > MAXIMUM_LENGTH){
            throw new GameException.InvalidGameNameException(trimmedInput);
        }
    }
}