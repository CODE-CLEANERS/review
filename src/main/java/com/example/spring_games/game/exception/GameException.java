package com.example.spring_games.game.exception;

public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }

    public static class InvalidGameNameException extends GameException {
        public InvalidGameNameException(String input) {
            super(String.format("게임 이름의 길이는 100자 이하여야 합니다. - request info { input : %s }", input));
        }

        private InvalidGameNameException() {
            super("게임 이름은 공백이어서는 안됩니다");
        }

        public static InvalidGameNameException ofEmpty(){
            return new InvalidGameNameException();
        }
    }

    public static class GameNotFoundException extends GameException {
        public GameNotFoundException(Long gameId) {
            super(String.format("게임을 찾을 수 없습니다 - gameId: %s", gameId));
        }
    }
}
