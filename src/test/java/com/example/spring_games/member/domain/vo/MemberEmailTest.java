package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberEmailTest {
    @Test
    @DisplayName("회원 이메일은 null일 수 없다")
    void memberEmailNull() {
        assertThatThrownBy(() -> new MemberEmail(null))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "invalid.email", "@invalid", "invalid@email", "invalid@email.too-long-domain", "invalid@email.x"})
    void memberEmailValid(String invalidEmail) {
        String validEmail = "test@email.com";
        assertThatCode(() -> new MemberEmail(validEmail))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> new MemberEmail(invalidEmail))
                .isInstanceOf(MemberException.MemberEmailNotValidException.class);
    }
}