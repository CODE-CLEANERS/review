package com.example.spring_games.member.domain;

import com.example.spring_games.common.test_instance.GameCardFixture;
import com.example.spring_games.common.test_instance.MemberFixture;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.vo.Price;
import com.example.spring_games.member.application.dto.MemberInfoRequest;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.example.spring_games.common.test_instance.GameCardFixture.*;
import static com.example.spring_games.common.test_instance.GameFixture.*;
import static com.example.spring_games.common.test_instance.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {

    //TODO : 1. 실패하는 테스트 고치기 - > Cards 제거
    //TODO : 2. 픽스쳐를 매서드마다 초기화 해주기

    private static Member TEST_MEMBER;
    private static Member TEST_MEMBER2;
    private static Member TEST_MEMBER3;

    @BeforeEach
    void setUp() {
        TEST_MEMBER = new Member(
                new MemberName("TEST"),
                new MemberEmail("TEST@naver.com"),
                new MemberJoinDate("2024-01-01")
        );

        TEST_MEMBER2 = new Member(
                new MemberName("TEST"),
                new MemberEmail("TEST@naver.com"),
                new MemberJoinDate("2024-01-01")
        );

        TEST_MEMBER3 = new Member(
                new MemberName("TEST"),
                new MemberEmail("TEST@naver.com"),
                new MemberJoinDate("2024-01-01")
        );
    }

    @Test
    void updateMemberInfo() {

        MemberInfoRequest updateRequest = new MemberInfoRequest(
                "TESTT",
                "TEST2@naver.com",
                "2024-01-02"
        );

        TEST_MEMBER.updateMemberInfo(
                new MemberName(updateRequest.memberName()),
                new MemberEmail(updateRequest.memberEmail()),
                new MemberJoinDate(updateRequest.joinDate())
        );

        assertSoftly(
                softly -> {
                    softly.assertThat(TEST_MEMBER.getMemberName().getValue()).isEqualTo(updateRequest.memberName());
                    softly.assertThat(TEST_MEMBER.getEmail().getValue()).isEqualTo(updateRequest.memberEmail());
                    softly.assertThat(TEST_MEMBER.getJoinDate().getValue()).isEqualTo(updateRequest.joinDate());
                                            }
        );
    }

    @Test
    void testUpdateMemberLevel() {
        MemberLevel newLevel = MemberLevel.BRONZE;

        TEST_MEMBER.updateMemberLevel(newLevel);

        assertEquals(newLevel, TEST_MEMBER.getMemberLevel());
    }

    @Test
    @DisplayName("가격이 0이 아닌 카드의 갯수를 카운트한다")
    void getValidCardCount() {
        //given
        List<GameCard> oneValidCard = List.of(YU_GI_OH_NO_1, POKEMON_NO_3_FREE, MAGIC_THE_GATHERING_NO_4_FREE);
        List<GameCard> cardsOfTwoValidCards = List.of(YU_GI_OH_NO_1, MAGIC_THE_GATHERING_NO_2_PRICE_100, POKEMON_NO_3_FREE);
        List<GameCard> cardsOfThreeValidCards = List.of(MAGIC_THE_GATHERING_NO_1, MAGIC_THE_GATHERING_NO_2_PRICE_100, MAGIC_THE_GATHERING_NO_3);

        //when
        TEST_MEMBER.addCard(oneValidCard);
        long expectedOne = TEST_MEMBER.getValidCardCount();

        TEST_MEMBER2.addCard(cardsOfTwoValidCards);
        long expectTwo = TEST_MEMBER2.getValidCardCount();

        TEST_MEMBER3.addCard(cardsOfThreeValidCards);
        long expectThree = TEST_MEMBER3.getValidCardCount();

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(expectedOne).isEqualTo(1);
                    softly.assertThat(expectTwo).isEqualTo(2);
                    softly.assertThat(expectThree).isEqualTo(3);
                }
        );
    }

    @Test
    @DisplayName("골드 회원은 카드 리스트에 적어도 두 개 이상 종류의 게임을 가지고 있어야 한다")
    void hasMoreThanTwoTypeOfGame() {
        // given
        TEST.addCard(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, YU_GI_OH_NO_2_PRICE_100));
        TEST_MEMBER2.addCard(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, MAGIC_THE_GATHERING_NO_2_PRICE_100));;

        //when
        boolean expectTrue = TEST.isGoldLevelEligible();
        boolean expectFalse = TEST_MEMBER2.isGoldLevelEligible();

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(expectTrue).isTrue();
                    softly.assertThat(expectFalse).isFalse();
                }
        );
    }

    @Test
    @DisplayName("금액이 0이 아닌 카드가 4개 이상 있으면 골드 회원으로 분류한다.")
    void isGoldLevelEligible_byValidCardCount() {
        // given
        final GameCard magicTheGatheringCard = GameCardFixture.getCustomInstance(MAGIC_THE_GATHERING, new Price(1.0), 9999L);
        final GameCard yuGiOhCard = GameCardFixture.getCustomInstance(YU_GI_OH, new Price(1.0), 99999L);
        final GameCard pokemonCard = GameCardFixture.getCustomInstance(POKEMON, new Price(1.0), 9999999L);
        final GameCard anotherPokemon = GameCardFixture.getCustomInstance(POKEMON, new Price(1.0), 9999991L);

        TEST_MEMBER.addCard(List.of(magicTheGatheringCard, yuGiOhCard));
        TEST_MEMBER2.addCard(List.of(magicTheGatheringCard, yuGiOhCard, pokemonCard, anotherPokemon));
        TEST_MEMBER3.addCard(List.of(magicTheGatheringCard, yuGiOhCard, pokemonCard, MAGIC_THE_GATHERING_NO_4_FREE));

        //when
        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(TEST_MEMBER.isGoldLevelEligible()).isFalse();
                    softly.assertThat(TEST_MEMBER2.isGoldLevelEligible()).isTrue();
                    softly.assertThat(TEST_MEMBER3.isGoldLevelEligible()).isFalse();
                }
        );
    }

    @Test
    @DisplayName("2~3개의 카드의 금액 총액이 100달러 이상이면 골드 회원으로 분류한다.")
    void isGoldLevelEligible_hasMinimumCountOfCardsExceedingGoldLevelAmount() {
        //given
        // 둘의 가격을 합하면 99.99 < 100
        final GameCard magicTheGatheringCard = GameCardFixture.getCustomInstance(MAGIC_THE_GATHERING, new Price(1.0), 9999L);
        final GameCard yuGiOhCard = GameCardFixture.getCustomInstance(YU_GI_OH, new Price(98.99), 99999L);

        TEST_MEMBER.addCard(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, YU_GI_OH_NO_2_PRICE_100));
        TEST_MEMBER2.addCard(List.of(magicTheGatheringCard, yuGiOhCard));
        TEST_MEMBER3.addCard(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100));


        //when
        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(TEST_MEMBER.isGoldLevelEligible()).isTrue();
                    softly.assertThat(TEST_MEMBER2.isGoldLevelEligible()).isFalse();
                    softly.assertThat(TEST_MEMBER3.isGoldLevelEligible()).isFalse();
                }
        );
    }

    @Test
    @DisplayName("카드가 없으면 실버회원으로 판별하지 않는다")
    void isSilverLevelEligible_emptyCards() {
        TEST_MEMBER.addCard(Collections.emptyList());
        assertThat(TEST_MEMBER.isSilverLevelEligible()).isFalse();
    }


    @Test
    @DisplayName("최소한 1개의 유효 카드가 없다면 실버 회원으로 판별하지 않는다")
    void isSilverLevelEligible_freeCards() {
        //given
        final List<GameCard> freeCardsList = List.of(MAGIC_THE_GATHERING_NO_4_FREE, YU_GI_OH_NO_3_FREE, POKEMON_NO_3_FREE);
        TEST_MEMBER.addCard(freeCardsList);
        //when
        final boolean silverLevelEligible = TEST_MEMBER.isSilverLevelEligible();
        //then
        assertThat(silverLevelEligible).isFalse();
    }
}