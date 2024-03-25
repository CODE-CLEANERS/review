package com.example.spring_games.slack.application;

import com.example.spring_games.slack.application.dto.MemberLevelChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class MemberLevelChangeEventListener {

    private final SlackNotificationService slackNotificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void sendMemberLevelChangeMessage(MemberLevelChangeEvent memberLevelChangeEvent){
        slackNotificationService.sendMessage(memberLevelChangeEvent);
    }

}