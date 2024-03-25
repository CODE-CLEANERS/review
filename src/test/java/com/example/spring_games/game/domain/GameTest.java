package com.example.spring_games.game.domain;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.game.domain.vo.GameName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class GameTest extends ServiceTest {
    // 게임의 아이디가 일련번호의 첫 자리가 되므로 ServiceTest 를 extends 해주었다. (id가 생성되어야 하므로)

    @Test
    @DisplayName("게임의 일련번호는 게임의 아이디로 시작하는 9자리 랜덤한 수이다.")
    void makeSerialNumber() {
        // Given
        Game game = new Game(new GameName("Test Game"));
        entityProvider.saveGame(game);

        // when
        Long serialNumber = game.makeSerialNumber();

        assertSoftly(
                softly -> {
                    softly.assertThat(serialNumber.toString().length()).isEqualTo(9);
                    softly.assertThat(serialNumber / 1_000_000_00).isEqualTo(game.getId());
                }
        );
    }
}