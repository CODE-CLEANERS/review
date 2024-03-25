package com.example.spring_games.member.application;

import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import com.example.spring_games.slack.application.dto.MemberLevelChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLevelNotificationService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void notifyMemberLevelChanged(Long memberId, MemberName memberName, MemberLevel updatedLevel) {
        MemberLevelChangeEvent memberLevelChangeEvent = new MemberLevelChangeEvent(memberId, memberName, updatedLevel);
        applicationEventPublisher.publishEvent(memberLevelChangeEvent);
    }
}