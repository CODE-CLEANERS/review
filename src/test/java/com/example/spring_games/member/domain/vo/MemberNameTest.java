package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberNameTest {

    @Test
    @DisplayName("회원 이름은 null일 수 없다")
    void memberNameNull() {
        assertThatThrownBy(() -> new MemberName(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @DisplayName("회원 이름은 빈 값일 수 없다")
    @ValueSource(strings = {"   ", "  ", "        "})
    void memberNameEmpty(String empty) {
        assertThatThrownBy(() -> new MemberName(empty))
                .isInstanceOf(MemberException.MemberNameNotBlankException.class);
    }

    @ParameterizedTest
    @DisplayName("회원 이름은 2자 미만일 수 없다")
    @ValueSource(strings = {"가", "  a", "           b", "    D"})
    void memberNameTooShort(String shortNameInput) {
        assertThatThrownBy(() -> new MemberName(shortNameInput))
                .isInstanceOf(MemberException.MemberNameLengthNotValidException.class);
    }

    @ParameterizedTest
    @DisplayName("회원 이름은 100자 이상일 수 없다")
    @ValueSource(strings = {"C", "D", "a", "가"})
    void memberNameTooLong(String input){
        assertThatCode(() -> new MemberName(input.repeat(100)))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> new MemberName(input.repeat(101)))
                .isInstanceOf(MemberException.MemberNameLengthNotValidException.class);
    }

    @ParameterizedTest
    @DisplayName("회원 이름에 영문,한글 외 값이 들어올 수 없다")
    @ValueSource(strings = {".a", "b :", "cc1", "ab-", "   a.", ".   .", "abc*"})
    void memberNameInvalid(String invalidInput){
        assertThatThrownBy(() -> new MemberName(invalidInput))
                .isInstanceOf(MemberException.MemberNameNotValidException.class);
    }
}