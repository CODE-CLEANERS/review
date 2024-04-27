package com.example.spring_games.global.presentation;

import com.example.spring_games.game.exception.GameException;
import com.example.spring_games.game_card.exception.GameCardException;
import com.example.spring_games.member.exception.MemberException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {
    @ExceptionHandler(value = {
            MemberException.MemberEmailNotValidException.class,
            MemberException.MemberNameLengthNotValidException.class,
            MemberException.MemberNameNotBlankException.class,
            MemberException.MemberEmailNotValidException.class,
            MemberException.DuplicatedMemberEmailException.class,
            MemberException.InvalidJoinDateFormatException.class,
            MemberException.InvalidJoinDateRangeException.class,
            MemberException.JoinDateEmptyException.class,
            GameException.InvalidGameNameException.class,
            GameCardException.InvalidPriceException.class,
            GameCardException.InvalidTitleException.class,
    })
    public ResponseEntity<ErrorResponse> handleCustomBadRequestException(final RuntimeException exception) {
        final String message = exception.getMessage();
        log.warn(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final String defaultErrorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn(() -> defaultErrorMessage);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(defaultErrorMessage));
    }

    @ExceptionHandler(value = {
            MemberException.MemberNotFoundException.class,
            GameException.GameNotFoundException.class,
    })
    public ResponseEntity<ErrorResponse> handleCustomNotFoundException(final RuntimeException exception){
        final String message = exception.getMessage();
        log.warn(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(value = {
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        final String message = exception.getMessage();
        log.error("Unexpected error occurred: " + message, exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(value = {
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(final Exception exception){
        final String message = "데이터 무결성 위반 오류입니다. 에러 상세 내용 \n" + exception.getMessage();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(message));
    }
}
