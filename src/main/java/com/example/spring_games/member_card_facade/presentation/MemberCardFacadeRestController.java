package com.example.spring_games.member_card_facade.presentation;

import com.example.spring_games.member_card_facade.application.MemberCardFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCardFacadeRestController {
    private final MemberCardFacadeService memberCardFacadeService;

    @DeleteMapping("/members/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberCardFacadeService.deleteMember(memberId);
    }
}