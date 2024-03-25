package com.example.spring_games.member.application;

import com.example.spring_games.common.service.ServiceTest;
import com.example.spring_games.common.test_instance.MemberFixture;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.slack.application.dto.MemberLevelChangeEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RecordApplicationEvents
class MemberLevelNotificationServiceTest extends ServiceTest {

    @Autowired
    private MemberLevelNotificationService memberLevelNotificationService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Test
    @DisplayName("회원 등급 변경 시 이벤트가 정상적으로 발행된다.")
    void notifyMemberLevelChanged() {
        // given
        Member member = entityProvider.saveMember(MemberFixture.TEST);

        // when
        memberLevelNotificationService.notifyMemberLevelChanged(
                member.getId(),
                member.getMemberName(),
                member.getMemberLevel()
        );

        // then
        MemberLevelChangeEvent event = applicationEvents.stream(MemberLevelChangeEvent.class).findFirst().orElse(null);
        assertThat(event).isNotNull();
        assertSoftly(
                softly -> {
                    softly.assertThat(event.memberId()).isEqualTo(member.getId());
                    softly.assertThat(event.memberName()).isEqualTo(member.getMemberName());
                    softly.assertThat(event.memberLevel()).isEqualTo(member.getMemberLevel());
                }
        );
    }
}