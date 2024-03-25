package com.example.spring_games.game_card.domain;

import com.example.spring_games.common.test_instance.GameCardFixture;
import com.example.spring_games.game_card.domain.vo.Cards;
import com.example.spring_games.game_card.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.example.spring_games.common.test_instance.GameCardFixture.MAGIC_THE_GATHERING_NO_1;
import static com.example.spring_games.common.test_instance.GameCardFixture.MAGIC_THE_GATHERING_NO_2_PRICE_100;
import static com.example.spring_games.common.test_instance.GameCardFixture.MAGIC_THE_GATHERING_NO_3;
import static com.example.spring_games.common.test_instance.GameCardFixture.MAGIC_THE_GATHERING_NO_4_FREE;
import static com.example.spring_games.common.test_instance.GameCardFixture.POKEMON_NO_2_PRICE_100;
import static com.example.spring_games.common.test_instance.GameCardFixture.POKEMON_NO_3_FREE;
import static com.example.spring_games.common.test_instance.GameCardFixture.YU_GI_OH_NO_1;
import static com.example.spring_games.common.test_instance.GameCardFixture.YU_GI_OH_NO_2_PRICE_100;
import static com.example.spring_games.common.test_instance.GameCardFixture.YU_GI_OH_NO_3_FREE;
import static com.example.spring_games.common.test_instance.GameFixture.MAGIC_THE_GATHERING;
import static com.example.spring_games.common.test_instance.GameFixture.POKEMON;
import static com.example.spring_games.common.test_instance.GameFixture.YU_GI_OH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class CardsTest {

    @Test
    @DisplayName("카드들의 금액 총합을 구한다")
    void getTotalAmount() {
        List<GameCard> threeCardsWithPrice100 = List.of(YU_GI_OH_NO_2_PRICE_100, MAGIC_THE_GATHERING_NO_2_PRICE_100, POKEMON_NO_2_PRICE_100);
        Cards cards = new Cards(threeCardsWithPrice100);

        assertThat(cards.getTotalAmount()).isEqualTo(300);
    }

    @Test
    @DisplayName("가격이 0이 아닌 카드의 갯수를 카운트한다")
    void getValidCardCount() {
        //given
        Cards cardsOfOneValidCard = new Cards(List.of(YU_GI_OH_NO_1, POKEMON_NO_3_FREE, MAGIC_THE_GATHERING_NO_4_FREE));
        Cards cardsOfTwoValidCards = new Cards(List.of(YU_GI_OH_NO_1, MAGIC_THE_GATHERING_NO_2_PRICE_100, POKEMON_NO_3_FREE));
        Cards cardsOfThreeValidCards = new Cards(List.of(MAGIC_THE_GATHERING_NO_1, MAGIC_THE_GATHERING_NO_2_PRICE_100, MAGIC_THE_GATHERING_NO_3));

        //when
        long expectedOne = cardsOfOneValidCard.getValidCardCount();
        long expectTwo = cardsOfTwoValidCards.getValidCardCount();
        long expectThree = cardsOfThreeValidCards.getValidCardCount();

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
        final Cards cardsContainLessThanTwoTypes = new Cards(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, MAGIC_THE_GATHERING_NO_2_PRICE_100));
        final Cards cardsContainMoreThanTwoTypes = new Cards(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, YU_GI_OH_NO_2_PRICE_100));

        //when
        boolean expectFalse = cardsContainLessThanTwoTypes.isGoldLevelEligible();
        boolean expectTrue = cardsContainMoreThanTwoTypes.isGoldLevelEligible();

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(expectFalse).isFalse();
                    softly.assertThat(expectTrue).isTrue();
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

        final Cards cardsSizeLessThanFour = new Cards(List.of(magicTheGatheringCard, yuGiOhCard));
        final Cards cardsSizeIsFour = new Cards(
                List.of(magicTheGatheringCard, yuGiOhCard, pokemonCard, anotherPokemon)
        );
        final Cards sizeIsFourButContainsFree = new Cards(
                List.of(magicTheGatheringCard, yuGiOhCard, pokemonCard, MAGIC_THE_GATHERING_NO_4_FREE)
        );

        //when
        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(cardsSizeLessThanFour.isGoldLevelEligible()).isFalse();
                    softly.assertThat(cardsSizeIsFour.isGoldLevelEligible()).isTrue();
                    softly.assertThat(sizeIsFourButContainsFree.isGoldLevelEligible()).isFalse();
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

        final Cards cardsHasMinimumCountOfCardsExceedingGoldLevel = new Cards(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100, YU_GI_OH_NO_2_PRICE_100));
        final Cards cardsHasNotMinimumCountOfCardsExceedingGoldLevel = new Cards(List.of(magicTheGatheringCard, yuGiOhCard));
        final Cards oneCardExceedingGoldLevelStandardAmount = new Cards(List.of(MAGIC_THE_GATHERING_NO_2_PRICE_100));

        //when
        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(cardsHasMinimumCountOfCardsExceedingGoldLevel.isGoldLevelEligible()).isTrue();
                    softly.assertThat(cardsHasNotMinimumCountOfCardsExceedingGoldLevel.isGoldLevelEligible()).isFalse();
                    softly.assertThat(oneCardExceedingGoldLevelStandardAmount.isGoldLevelEligible()).isFalse();
                }
        );
    }

    @Test
    @DisplayName("카드가 없으면 실버회원으로 판별하지 않는다")
    void isSilverLevelEligible_emptyCards() {
        final Cards emptyCards = new Cards(Collections.emptyList());
        assertThat(emptyCards.isSilverLevelEligible()).isFalse();
    }

    @Test
    @DisplayName("최소한 1개의 유효 카드가 없다면 실버 회원으로 판별하지 않는다")
    void isSilverLevelEligible_freeCards() {
        //given
        final List<GameCard> freeCardsList = List.of(MAGIC_THE_GATHERING_NO_4_FREE, YU_GI_OH_NO_3_FREE, POKEMON_NO_3_FREE);
        final Cards freeCards = new Cards(freeCardsList);
        //when
        final boolean silverLevelEligible = freeCards.isSilverLevelEligible();
        //then
        assertThat(silverLevelEligible).isFalse();
    }
}