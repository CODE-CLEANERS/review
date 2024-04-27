package com.example.spring_games.common.test_instance;

import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game.domain.vo.GameName;

public class GameFixture {
    public static final Game MAGIC_THE_GATHERING = new Game(
            new GameName("매직 더 게더링")
    );
    public static final Game YU_GI_OH = new Game(
            new GameName("유희왕")
    );
    public static final Game POKEMON = new Game(
            new GameName("포켓몬")
    );

    public static final Game GAME_FOR_SEPERATED_TRANSACTION = new Game(
            new GameName("TEST")
    );
}
