package com.example.spring_games.slack.application;

import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import com.example.spring_games.slack.application.dto.MemberLevelChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(classes = { MemberLevelChangeEventListener.class, SlackNotificationService.class })
class MemberLevelChangeEventListenerTest {

    private static final String SLACK_SERVICE_FIELD_NAME = "webhookUrl";

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @SpyBean
    private SlackNotificationService slackNotificationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(slackNotificationService, SLACK_SERVICE_FIELD_NAME, "https://TEST-URL");
    }

    @Test
    void shouldCallSendMessageOnServiceWhenEventIsReceived() {
        MemberLevelChangeEvent givenEvent =
                new MemberLevelChangeEvent(1L, new MemberName("TEST"), MemberLevel.BRONZE);

        // when
        applicationEventPublisher.publishEvent(givenEvent);

        // then
        await().atMost(5, SECONDS).untilAsserted(() ->
                verify(slackNotificationService, times(1)).sendMessage(any(MemberLevelChangeEvent.class)));
    }
}