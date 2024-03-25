package com.example.spring_games.member.exception;

import java.time.LocalDate;

public class MemberException extends RuntimeException {
    public MemberException(String message) {
        super(message);
    }

    public static class MemberNameNotBlankException extends MemberException {

        public MemberNameNotBlankException(String input) {
            super(String.format("회원 이름은 빈 값일 수 없습니다. - request info { input : %s }", input));
        }
    }

    public static class MemberNameLengthNotValidException extends MemberException {
        public MemberNameLengthNotValidException(String input) {
            super(String.format("회원 이름은 2글자에서 100글자까지 가능합니다. - request info { input : %s }", input));
        }
    }

    public static class MemberNameNotValidException extends MemberException {
        public MemberNameNotValidException(String input) {
            super(String.format("회원 이름은 한글 혹은 영문으로만 이루어져야 합니다. - request info { input : %s }", input));
        }
    }

    public static class MemberEmailNotValidException extends MemberException {
        public MemberEmailNotValidException(String input) {
            super(String.format("유효하지 않은 이메일 형식입니다. - request info { input : %s }", input));
        }
    }

    public static class DuplicatedMemberEmailException extends MemberException {
        public DuplicatedMemberEmailException(String input) {
            super(String.format("중복된 이메일입니다. - request info { input : %s }", input));
        }
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(Long memberId) {
            super(String.format("회원을 찾을 수 없습니다. - request info { memberId : %s }", memberId));
        }
    }

    public static class JoinDateEmptyException extends MemberException {
        public JoinDateEmptyException(String input) {
            super(String.format("가입일은 빈 값일 수 없습니다. - request info { input : %s }", input));
        }
    }

    public static class InvalidJoinDateFormatException extends MemberException {
        public InvalidJoinDateFormatException(String input) {
            super(String.format("유효하지 않은 날짜 및 시간 형식입니다. - request info { input : %s }", input));
        }
    }

    public static class InvalidJoinDateRangeException extends MemberException {
        public InvalidJoinDateRangeException(LocalDate input) {
            super(String.format("1년 이내 날짜만 입력 가능합니다 - request info { input : %s, allowed : %s }", input, LocalDate.now().minusYears(1)));
        }
    }
}
