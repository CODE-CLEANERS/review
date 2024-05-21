package com.example.spring_games.member.application;

import com.example.spring_games.member.application.dto.MemberInfoRequest;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    @Transactional
    Long signup(MemberInfoRequest memberInfoRequest);

}
