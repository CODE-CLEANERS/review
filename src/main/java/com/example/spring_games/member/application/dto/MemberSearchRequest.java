package com.example.spring_games.member.application.dto;

import com.example.spring_games.member.domain.vo.MemberLevel;
import lombok.Data;

@Data
public class MemberSearchRequest {
    private String memberName;
    private MemberLevel memberLevel;
}