package com.example.spring_games.game_card.exception;

public class GameCardException extends RuntimeException {
    public GameCardException(final String message) {
        super(message);
    }

    public static class InvalidTitleException extends GameCardException{

        public InvalidTitleException(String input) {
            super(String.format("타이틀의 길이는 1~100 사이여야 합니다. - request info { input : %s }", input));
        }
    }

    public static class InvalidPriceException extends GameCardException {
        public InvalidPriceException(double input) {
            super(String.format("가격은 0 또는 그 이상, $100,000 이하의 달러 금액이어야 합니다. - request info { input : %s }", input));
        }
    }
}
