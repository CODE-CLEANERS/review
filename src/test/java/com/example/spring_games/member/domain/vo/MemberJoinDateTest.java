package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberJoinDateTest {
    @Test
    @DisplayName("가입 날짜는 null일 수 없다")
    void memberJoinDateNull() {
        assertThatThrownBy(() -> new MemberJoinDate(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("가입 날짜는 빈 문자열일 수 없다")
    void memberJoinDateEmpty() {
        assertThatThrownBy(() -> new MemberJoinDate(""))
                .isInstanceOf(MemberException.JoinDateEmptyException.class);
    }

    @ParameterizedTest
    @DisplayName("가입 날짜의 형식이 잘못되었을 경우 예외가 발생한다")
    @ValueSource(strings = {"2023-13-01", "2023-12-32", "20231101", "2023-1101", "2024-11", "2023:11:01"})
        // 위와 같은 잘못된 형식의 날짜를 사용
    void memberJoinDateInvalidFormat(String invalidFormat) {
        assertThatThrownBy(() -> new MemberJoinDate(invalidFormat))
                .isInstanceOf(MemberException.InvalidJoinDateFormatException.class);
    }

    @Test
    @DisplayName("가입 날짜가 1년 전보다 이르면 예외가 발생한다")
    void memberJoinDateOutOfRange() {
        final LocalDate validDate = LocalDate.now().minusYears(1);
        assertThatNoException().isThrownBy(() -> new MemberJoinDate(String.valueOf(validDate)));

        final LocalDate invalidDate = validDate.minusDays(1);
        assertThatThrownBy(() -> new MemberJoinDate(String.valueOf(invalidDate)))
                .isInstanceOf(MemberException.InvalidJoinDateRangeException.class);
    }
}