package com.example.spring_games.game_card.domain.vo;

import com.example.spring_games.game_card.exception.GameCardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest {
    @Test
    @DisplayName("게임카드 타이틀은 null일 수 없다")
    void titleNull() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @ValueSource(strings = {"   ", "  ", "        "})
    @ParameterizedTest
    @DisplayName("게임카드 타이틀은 빈 값일 수 없다")
    void titleEmpty(String emptyString) {
        assertThatThrownBy(() -> new Title(emptyString)).isInstanceOf(GameCardException.InvalidTitleException.class);
    }

    @ValueSource(strings = {"C", "D", "a", "가", "1", ".", ";", "?"})
    @ParameterizedTest
    @DisplayName("게임카드 타이틀은 100자 이상일 수 없다")
    void memberNameTooLong(String input){
        assertThatNoException().isThrownBy(() -> new Title(input.repeat(100)));
        assertThatThrownBy(() -> new Title(input.repeat(101))).isInstanceOf(GameCardException.InvalidTitleException.class);
    }
}