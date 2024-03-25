package com.example.spring_games.game_card.domain.vo;

import com.example.spring_games.game_card.exception.GameCardException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest
    @CsvSource(delimiter = ':',
            value = {
                    "99999.995:100000",
                    "0.005:0.01",
                    "0.004:0",
                    "123.456:123.46",
                    "0.555:0.56",
            })
    @DisplayName("Price 는 입력받은 값의 소수점 2자리까지 표현되며, 세자리에서 반올림한다.")
    void roundToTwoDecimals(double value, double roundedValue) {
        Price price = new Price(value);
        assertThat(roundedValue).isEqualTo(price.getValue());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 100000.005})
    @DisplayName("Price 는 0~100,000 사이의 값이어야 한다")
    void invalidPrice(double value) {
        assertThatThrownBy(() -> new Price(value)).isInstanceOf(GameCardException.InvalidPriceException.class);
    }
}