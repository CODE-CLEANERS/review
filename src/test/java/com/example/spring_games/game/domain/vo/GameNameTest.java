package com.example.spring_games.game.domain.vo;

import com.example.spring_games.game.exception.GameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameNameTest {
    @Test
    @DisplayName("게임 이름은 null일 수 없다")
    void gameNameNull() {
        assertThatThrownBy(() -> new GameName(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "  ", "        "})
    @DisplayName("게임 이름은 빈 값일 수 없다")
    void gameNameEmpty(String empty) {
        assertThatThrownBy(() -> new GameName(empty))
                .isInstanceOf(GameException.InvalidGameNameException.class);
    }

    @ParameterizedTest
    @DisplayName("게임 이름은 100자 이상일 수 없다")
    @ValueSource(strings = {"C", "D", "a", "가"})
    void gameNameTooLong(String input){
        assertThatCode(() -> new GameName(input.repeat(100)))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> new GameName(input.repeat(101)))
                .isInstanceOf(GameException.InvalidGameNameException.class);
    }
}