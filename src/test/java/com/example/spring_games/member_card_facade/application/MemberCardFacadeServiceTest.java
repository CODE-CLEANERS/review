package com.example.spring_games.member_card_facade.application;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.common.test_instance.GameCardFixture;
import com.example.spring_games.common.test_instance.GameFixture;
import com.example.spring_games.common.test_instance.MemberFixture;
import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.member.application.dto.MemberSearchRequest;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member_card_facade.application.dto.CardDetailResponse;
import com.example.spring_games.member_card_facade.application.dto.MemberCardResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MemberCardFacadeServiceTest extends ServiceTest {
    @Autowired
    private MemberCardFacadeService memberCardFacadeService;

    @Test
    @DisplayName("회원 정보와 카드 갯수를 불러 오는 데 성공한다.")
    void getSingleMemberWithCardCount() {
        // given
        Member member = entityProvider.saveMember(MemberFixture.MEMBER_DK);

        // when
        MemberCardResponse response = memberCardFacadeService.getSingleMemberWithCardCount(member.getId());

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.memberId()).isEqualTo(member.getId());
                    softly.assertThat(response.memberName()).isEqualTo(member.getMemberName().getValue());
                    softly.assertThat(response.memberLevel()).isEqualTo(member.getMemberLevel());
                    softly.assertThat(response.cardCount()).isZero();
                    softly.assertThat(response.joinDate()).isEqualTo(member.getJoinDate().getValue());
                }
        );
    }


    @Test
    @DisplayName("다중 회원 정보와 카드 갯수를 불러 오는 데 성공한다.")
    void getMultipleMembersWithCardCounts() {
        // given
        Member member1 = entityProvider.saveMember(MemberFixture.MEMBER_DK);
        Member member2 = entityProvider.saveMember(MemberFixture.MEMBER_DK_2);
        MemberSearchRequest searchRequest = new MemberSearchRequest();

        List<Member> members = Stream.of(member1, member2)
                .sorted(Comparator.comparing(Member::getId).reversed()).toList();

        // when
        List<MemberCardResponse> responses = memberCardFacadeService.getMultipleMembersWithCardCounts(searchRequest);

        // then
        for (int i = 0; i < responses.size(); i++) {
            MemberCardResponse response = responses.get(i);
            Member member = members.get(i);
            assertSoftly(
                    softly -> {
                        softly.assertThat(response.memberId()).isEqualTo(member.getId());
                        softly.assertThat(response.memberName()).isEqualTo(member.getMemberName().getValue());
                        softly.assertThat(response.memberLevel()).isEqualTo(member.getMemberLevel());
                        softly.assertThat(response.cardCount()).isZero();
                        softly.assertThat(response.joinDate()).isEqualTo(member.getJoinDate().getValue());
                    }
            );
        }
    }

    @Test
    @DisplayName("회원이 가진 카드 목록 조회에 성공한다.")
    void getGameCardsOfMember() {
        // given
        Game game = entityProvider.saveGame(GameFixture.MAGIC_THE_GATHERING);
        Member member = entityProvider.saveMember(MemberFixture.MEMBER_DK);
        GameCard gameCard1 = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(game, member, 1L));
        GameCard gameCard2 = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(game, member, 2L));
        GameCard gameCard3 = entityProvider.saveGameCard(GameCardFixture.getCustomInstance(game, member, 3L));

        //when
        member.addCard(List.of(gameCard1, gameCard2, gameCard3));
        List<CardDetailResponse> gameCardsOfMember = memberCardFacadeService.getGameCardsOfMember(member.getId());

        // then
        assertThat(gameCardsOfMember.getFirst()).isEqualTo(CardDetailResponse.of(gameCard3));
        assertThat(gameCardsOfMember.get(1)).isEqualTo(CardDetailResponse.of(gameCard2));
        assertThat(gameCardsOfMember.get(2)).isEqualTo(CardDetailResponse.of(gameCard1));
    }
}