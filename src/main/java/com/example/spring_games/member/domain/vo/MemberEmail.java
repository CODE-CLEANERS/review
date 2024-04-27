package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class MemberEmail {
    private static final String REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    @Column(name = "email", unique = true, nullable = false)
    private String value;

    public MemberEmail(String input) {
        validateNull(input);
        validateEmailFormat(input);
        this.value = input;
    }

    private static void validateNull(String input){
        if (Objects.isNull(input)){
            throw new NullPointerException("이메일은 null일 수 없습니다.");
        }
    }

    private static void validateEmailFormat(String input) {
        if (!Pattern.matches(REGEX, input)){
            throw new MemberException.MemberEmailNotValidException(input);
        }
    }
}
