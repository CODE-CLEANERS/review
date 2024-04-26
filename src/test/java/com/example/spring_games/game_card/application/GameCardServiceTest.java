package com.example.spring_games.game_card.application;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.common.test_instance.GameCardFixture;
import com.example.spring_games.common.test_instance.GameFixture;
import com.example.spring_games.common.test_instance.MemberFixture;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.application.dto.GameCardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameCardServiceTest extends ServiceTest {

    private static final Member TEST_MEMBER = MemberFixture.TEST;

    @Autowired
    private GameCardService gameCardService;

    @BeforeEach
    void setUp() {
        entityProvider.saveGame(GameFixture.POKEMON);
        entityProvider.saveGame(GameFixture.YU_GI_OH);
        entityProvider.saveGame(GameFixture.MAGIC_THE_GATHERING);
        entityProvider.saveMember(TEST_MEMBER);
    }

}