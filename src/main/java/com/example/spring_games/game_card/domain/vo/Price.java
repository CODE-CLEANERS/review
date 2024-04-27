package com.example.spring_games.game_card.domain.vo;

import com.example.spring_games.game_card.exception.GameCardException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Price {
    private static final double MIN_VALUE = 0.0;
    private static final double MAX_VALUE = 100000.0;
    private static final double SCALE = 100;
    private static final int GOLD_LEVEL_STANDARD_PRICE = 100;

    @Column(name = "price")
    private double value;

    public Price(final double input) {
        final double roundedPrice = roundToTwoDecimals(input);
        isValidValue(roundedPrice);
        this.value = roundedPrice;
    }

    private static void isValidValue(double input) {
        if (input < MIN_VALUE || input > MAX_VALUE){
            throw new GameCardException.InvalidPriceException(input);
        }
    }

    private static double roundToTwoDecimals(double input) {
        return Math.round(input * SCALE) / SCALE;
    }
}
