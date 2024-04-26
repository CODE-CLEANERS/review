package com.example.spring_games.common.test_instance;

import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.vo.Price;
import com.example.spring_games.game_card.domain.vo.Title;
import com.example.spring_games.member.domain.Member;

public class GameCardFixture {
    private static final int FREE = 0;
    private static final int NOT_GOLD_LEVEL_PRICE = 99;
    private static final int GOLD_LEVEL_STANDARD_PRICE = 100;

    public static GameCard getCustomInstance(Game gameFixture, Price price, Long serialNumber){
        return new GameCard(
                new Title("TEST"),
                price,
                serialNumber,
                gameFixture
        );
    }

    public static GameCard getCustomInstance(Game game, Member member, Long serialNumber){
        return new GameCard(
                new Title("TEST"),
                new Price(100),
                serialNumber,
                game
        );
    }

    public static final GameCard MAGIC_THE_GATHERING_DK = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            1125126126L,
            GameFixture.MAGIC_THE_GATHERING
    );

    public static final GameCard YU_GI_OH_DK = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            125125L,
            GameFixture.YU_GI_OH
    );

    public static final GameCard MAGIC_THE_GATHERING_NO_1 = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            1L,
            GameFixture.MAGIC_THE_GATHERING
            );

    public static final GameCard MAGIC_THE_GATHERING_NO_2_PRICE_100 = new GameCard(
            new Title("TEST"),
            new Price(GOLD_LEVEL_STANDARD_PRICE),
            2L,
            GameFixture.MAGIC_THE_GATHERING
    );

    public static final GameCard MAGIC_THE_GATHERING_NO_3 = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            3L,
            GameFixture.MAGIC_THE_GATHERING
    );

    public static final GameCard MAGIC_THE_GATHERING_NO_4_FREE = new GameCard(
            new Title("TEST"),
            new Price(FREE),
            3L,
            GameFixture.MAGIC_THE_GATHERING
    );

    public static final GameCard YU_GI_OH_NO_1 = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            1L,
            GameFixture.YU_GI_OH
    );

    public static final GameCard YU_GI_OH_NO_2_PRICE_100 = new GameCard(
            new Title("TEST"),
            new Price(GOLD_LEVEL_STANDARD_PRICE),
            2L,
            GameFixture.YU_GI_OH
    );

    public static final GameCard YU_GI_OH_NO_3_FREE = new GameCard(
            new Title("TEST"),
            new Price(FREE),
            3L,
            GameFixture.YU_GI_OH
    );

    public static final GameCard POKEMON_NO_1 = new GameCard(
            new Title("TEST"),
            new Price(NOT_GOLD_LEVEL_PRICE),
            1L,
            GameFixture.POKEMON
    );

    public static final GameCard POKEMON_NO_2_PRICE_100 = new GameCard(
            new Title("TEST"),
            new Price(GOLD_LEVEL_STANDARD_PRICE),
            2L,
            GameFixture.POKEMON
    );

    public static final GameCard POKEMON_NO_3_FREE = new GameCard(
            new Title("TEST"),
            new Price(FREE),
            1L,
            GameFixture.POKEMON
    );

    public static final GameCard MAGIC_THE_GATHERING_LOW_PRICE = getCustomInstance(
            GameFixture.MAGIC_THE_GATHERING,
            new Price(1.0),
            9999L
    );

    public static final GameCard YU_GI_OH_HIGH_PRICE = getCustomInstance(
            GameFixture.YU_GI_OH,
            new Price(98.09),
            99999L
    );

    public static final GameCard ANOTHER_POKEMON_LOW_PRICE = getCustomInstance(
            GameFixture.POKEMON,
            new Price(1.0),
            9999991L
    );

    public static final GameCard POKEMON_MIDDLE_PRICE = getCustomInstance(
            GameFixture.POKEMON,
            new Price(50.0),
            1234567L
    );
}
