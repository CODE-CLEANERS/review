package com.example.spring_games.member_card_facade.application.dto;

import com.example.spring_games.member.domain.vo.MemberLevel;

import java.time.LocalDate;

public record MemberCardResponse(Long memberId,
                                 String memberName,
                                 String memberEmail,
                                 LocalDate joinDate,
                                 MemberLevel memberLevel,
                                 Long cardCount) {
}
