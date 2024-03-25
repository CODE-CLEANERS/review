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

    @Test
    @DisplayName("회원의 카드 수를 조회한다")
    void getGameCardCount() {
        // given
        Game magicTheGathering = entityProvider.saveGame(GameFixture.MAGIC_THE_GATHERING);
        Game pokemonGame = entityProvider.saveGame(GameFixture.POKEMON);
        Member member = entityProvider.saveMember(TEST_MEMBER);


        GameCard magicTheGatheringCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(magicTheGathering, member, 1L));
        GameCard pokemonCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(pokemonGame, member, 1L));
        GameCard yuGiOhCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(magicTheGathering, member, 2L));

        // when
        Long gameCardCount = gameCardService.getGameCardCount(TEST_MEMBER.getId());

        // then
        assertThat(gameCardCount).isEqualTo(3);
    }

    @Test
    @DisplayName("게임 카드 등록에 성공한다")
    void enrollGameCard() {
        //given - 일련 번호 생성 시 REQUIRES_NEW 전파 전략을 사용하므로, 테스트 트랜잭션이 아닌 새 트랜잭션으로 엔티티를 저장 후 사용한다.
        Game game = entityProvider.saveGameOnSeperatedTransaction(GameFixture.GAME_FOR_SEPERATED_TRANSACTION);
        Member member = entityProvider.saveMemberOnSeperatedTransaction(MemberFixture.TEST_MEMBER_FOR_SEPERATED_TRANSACTION);

        GameCardRequest gameCardRequest = new GameCardRequest(
                "title",
                100.0,
                member.getId(),
                game.getId()
        );

        //when
        gameCardService.enrollGameCard(gameCardRequest);

        //then
        List<GameCard> memberGameCards = gameCardService.findAllByMemberId(member.getId());
        assertThat(memberGameCards).hasSize(1);
    }

    @Test
    @DisplayName("회원의 모든 카드의 조회에 성공한다")
    void findAllByMemberId() {
        // given
        Game magicTheGathering = entityProvider.saveGame(GameFixture.MAGIC_THE_GATHERING);
        Game pokemonGame = entityProvider.saveGame(GameFixture.POKEMON);
        Member member = entityProvider.saveMember(TEST_MEMBER);

        GameCard magicTheGatheringCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(magicTheGathering, member, 1L));
        GameCard pokemonCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(pokemonGame, member, 1L));
        GameCard yuGiOhCard = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(magicTheGathering, member, 2L));

        // when
        List<GameCard> memberGameCards = gameCardService.findAllByMemberId(TEST_MEMBER.getId());

        // then
        List<GameCard> expected = List.of(magicTheGatheringCard, pokemonCard, yuGiOhCard);
        assertThat(memberGameCards).isEqualTo(expected);
    }
}