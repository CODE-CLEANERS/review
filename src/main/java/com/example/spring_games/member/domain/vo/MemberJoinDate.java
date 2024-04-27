package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class MemberJoinDate {

    @Column(name = "join_date", nullable = false)
    private LocalDate value;

    public MemberJoinDate(String input) {
        validateNotNull(input);
        validateNotEmpty(input);
        final LocalDate parsedDate = validateDateFormat(input);
        validateWithinOneYear(parsedDate);
        this.value = parsedDate;
    }

    private void validateNotNull(String input){
        if (input == null) {
            throw new NullPointerException("가입 날짜는 null일 수 없습니다.");
        }
    }

    private void validateNotEmpty(String input) {
        if(input.isEmpty()) {
            throw new MemberException.JoinDateEmptyException(input);
        }

    }

    private LocalDate validateDateFormat(String input) {
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            throw new MemberException.InvalidJoinDateFormatException(input);
        }
    }

    private void validateWithinOneYear(LocalDate joinDate) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        if (joinDate.isBefore(oneYearAgo)) {
            throw new MemberException.InvalidJoinDateRangeException(joinDate);
        }
    }
}
