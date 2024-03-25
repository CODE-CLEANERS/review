package com.example.spring_games.slack.application.dto;

import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;

public record MemberLevelChangeEvent(Long memberId, MemberName memberName, MemberLevel memberLevel) {
}
