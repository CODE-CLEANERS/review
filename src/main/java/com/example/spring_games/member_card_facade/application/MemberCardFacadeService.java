package com.example.spring_games.member_card_facade.application;

import com.example.spring_games.game_card.application.GameCardService;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.member.application.MemberService;
import com.example.spring_games.member.application.dto.MemberSearchRequest;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member_card_facade.application.dto.CardDetailResponse;
import com.example.spring_games.member_card_facade.application.dto.MemberCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCardFacadeService {
    private final MemberService memberService;
    private final GameCardService gameCardService;

    public MemberCardResponse getSingleMemberWithCardCount(Long memberId){
        Member member = memberService.findById(memberId);
        Long gameCardCount = gameCardService.getGameCardCount(memberId);
        return new MemberCardResponse(
                memberId,
                member.getMemberName().getValue(),
                member.getEmail().getValue(),
                member.getJoinDate().getValue(),
                member.getMemberLevel(),
                gameCardCount
        );
    }

    @Transactional(readOnly = true)
    public List<MemberCardResponse> getMultipleMembersWithCardCounts(MemberSearchRequest memberSearchRequest){
        List<Member> members = memberService.findAllByMemberNameValueAndMemberLevel(memberSearchRequest.getMemberName(), memberSearchRequest.getMemberLevel());
        return members.stream()
                .mapToLong(Member::getId)
                .mapToObj(this::getSingleMemberWithCardCount)
                .sorted(Comparator.comparing(MemberCardResponse::memberId).reversed())
                .toList();
    }

    public List<CardDetailResponse> getGameCardsOfMember(Long memberId){
        List<GameCard> memberCards = gameCardService.findAllByMemberId(memberId);
        return memberCards.stream()
                .map(CardDetailResponse::of)
                .sorted(Comparator.comparing(CardDetailResponse::id).reversed())
                .toList();
    }
    @Transactional
    public void deleteMember(Long memberId){
        memberService.deleteMember(memberId);
        gameCardService.deleteAllByMemberId(memberId);
    }
}