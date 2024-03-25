package com.example.spring_games.slack.application;


import com.example.spring_games.slack.application.dto.MemberLevelChangeEvent;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class SlackNotificationService {
    @Value("${slack.webhook.test.url}")
    private String testWebhookUrl;

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private static final String APPLICANT_NAME = "[지원자 : 김동균]";
    private static final String MEMBER_ID_MESSAGE_TEMPLATE = "[memberId : %s]";
    private static final String MEMBER_NAME_MESSAGE_TEMPLATE = "[memberName : %s]";
    private static final String MEMBER_LEVEL_CHANGE_MESSAGE_TEMPLATE = "회원 등급이 %s로 변경되었습니다.";
    private static final Slack SLACK = Slack.getInstance();

    public void sendMessage(MemberLevelChangeEvent memberLevelChangeEvent) {
        final Payload payload = generatePayload(memberLevelChangeEvent);
        try {
            SLACK.send(webhookUrl, payload);
        } catch (IOException e){
            log.warn("슬랙 메시지 전송 실패, ex : {}", () -> e);
        }
    }

    private static Payload generatePayload(MemberLevelChangeEvent memberLevelChangeEvent) {
        final String memberIdMessage = String.format(MEMBER_ID_MESSAGE_TEMPLATE, memberLevelChangeEvent.memberId());
        final String memberNameMessage = String.format(MEMBER_NAME_MESSAGE_TEMPLATE, memberLevelChangeEvent.memberName().getValue());
        final String memberLevelMessage = String.format(MEMBER_LEVEL_CHANGE_MESSAGE_TEMPLATE, memberLevelChangeEvent.memberLevel());
        final String slackMessage = String.join("\n", APPLICANT_NAME, memberIdMessage, memberNameMessage, memberLevelMessage);
        return Payload.builder().text(slackMessage).build();
    }
}
