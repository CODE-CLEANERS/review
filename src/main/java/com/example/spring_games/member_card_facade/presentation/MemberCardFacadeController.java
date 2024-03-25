package com.example.spring_games.member_card_facade.presentation;

import com.example.spring_games.member.application.dto.MemberSearchRequest;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member_card_facade.application.MemberCardFacadeService;
import com.example.spring_games.member_card_facade.application.dto.CardDetailResponse;
import com.example.spring_games.member_card_facade.application.dto.MemberCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberCardFacadeController {

    private final MemberCardFacadeService memberCardFacadeService;

    @GetMapping("/members")
    public String getMultipleMembersWithCardCounts(Model model, @ModelAttribute MemberSearchRequest memberSearchRequest){
        List<MemberCardResponse> memberCardResponsesWithCounts = memberCardFacadeService.getMultipleMembersWithCardCounts(memberSearchRequest);
        model.addAttribute("members", memberCardResponsesWithCounts);
        model.addAttribute("memberSearchRequest", memberSearchRequest);
        model.addAttribute("memberLevels", Arrays.asList(MemberLevel.values()));
        return "membersList";
    }

    @GetMapping("/members/{memberId}")
    public String memberDetail(Model model, @PathVariable Long memberId){
        MemberCardResponse singleMemberWithCardCount = memberCardFacadeService.getSingleMemberWithCardCount(memberId);
        List<CardDetailResponse> gameCardDetails = memberCardFacadeService.getGameCardsOfMember(memberId);
        model.addAttribute("member", singleMemberWithCardCount);
        model.addAttribute("memberCards", gameCardDetails);
        return "member_detail";
    }

    @GetMapping("/members/{memberId}/edit")
    public String memberInfoEdit(Model model, @PathVariable Long memberId){
        MemberCardResponse singleMemberWithCardCount = memberCardFacadeService.getSingleMemberWithCardCount(memberId);
        model.addAttribute("prevMemberInfo", singleMemberWithCardCount);
        return "updateForm";
    }
}