package com.example.spring_games.game.application;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.common.test_instance.GameFixture;
import com.example.spring_games.game.application.dto.GameResponse;
import com.example.spring_games.game.domain.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class GameServiceTest extends ServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    @DisplayName("게임 조회에 성공한다")
    void findByIdTest() {
        Game magicTheGathering = GameFixture.MAGIC_THE_GATHERING;
        entityProvider.saveGame(magicTheGathering);

        Game expected = gameService.findById(magicTheGathering.getId());

        assertThat(magicTheGathering).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 정보를 모두 조회한다")
    void getAllGameInfosTest() {
        Game game1 = GameFixture.MAGIC_THE_GATHERING;
        Game game2 = GameFixture.POKEMON;
        entityProvider.saveGame(game1);
        entityProvider.saveGame(game2);

        List<GameResponse> gameInfos = gameService.getAllGameInfos();

        assertThat(gameInfos).hasSize(2);

        assertSoftly(
                softly -> {
                    softly.assertThat(gameInfos)
                            .extracting(GameResponse::gameId)
                            .containsExactly(game1.getId(), game2.getId());
                    softly.assertThat(gameInfos)
                            .extracting(GameResponse::gameName)
                            .containsExactly(game1.getGameName().getValue(), game2.getGameName().getValue());
                }
        );
    }
}