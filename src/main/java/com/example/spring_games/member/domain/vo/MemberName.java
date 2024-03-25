package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberName {

    private static final int MINIMAL_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 100;
    private static final String MEMBER_NAME_REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]*$";

    @Column(name = "name", nullable = false)
    private String value;

    public MemberName(String input) {
        validateNull(input);
        final String trimmedValue = input.trim();
        validateLength(trimmedValue);
        validateIsKoreanOrEnglish(trimmedValue);
        this.value = trimmedValue;
    }

    private static void validateNull(String input){
        if (Objects.isNull(input)) {
            throw new NullPointerException("회원 이름은 null일 수 없습니다.");
        }
    }

    private static void validateLength(String input) {
        if (input.isBlank()){
            throw new MemberException.MemberNameNotBlankException(input);
        }

        if (input.length() < MINIMAL_LENGTH || input.length() > MAXIMUM_LENGTH){
            throw new MemberException.MemberNameLengthNotValidException(input);
        }
    }

    private static void validateIsKoreanOrEnglish(String input) {
        if (!Pattern.matches(MEMBER_NAME_REGEX, input)){
            throw new MemberException.MemberNameNotValidException(input);
        }
    }
}
