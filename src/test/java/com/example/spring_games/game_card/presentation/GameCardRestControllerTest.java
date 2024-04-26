package com.example.spring_games.game_card.presentation;

import com.example.spring_games.game_card.application.GameCardService;
import com.example.spring_games.member.application.dto.GameCardRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameCardRestController.class)
class GameCardRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameCardService gameCardService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    @DisplayName("게임 카드 등록에 성공한다.")
    void enrollGameCardSuccess() throws Exception {
        GameCardRequest gameCardRequest = new GameCardRequest(
                "test",
                100.0,
                1L,
                1L
        );

        when(gameCardService.save(gameCardRequest)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     "})
    @DisplayName("게임 카드 등록 시 타이틀이 비었을 때 실패한다")
    void enrollGameCardTitleBlankError(String emptyTitle) throws Exception {
        GameCardRequest gameCardRequest = new GameCardRequest(
                emptyTitle,
                100.0,
                1L,
                1L
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게임 카드 등록 시 타이틀이 100 자 이상일 때 실패한다")
    void enrollGameCardTitleLengthTooLong() throws Exception {
        String lengthOneString = "1";
        int failCount = 101;
        GameCardRequest gameCardRequest = new GameCardRequest(
                lengthOneString.repeat(failCount),
                100.0,
                1L,
                1L
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -10.0, -100000.0})
    @DisplayName("게임 카드 등록 시 가격이 음수일 때 실패한다.")
    void enrollGameCardPriceNegativeError(double negativeValue) throws Exception {
        GameCardRequest gameCardRequest = new GameCardRequest(
                "test",
                negativeValue,
                1L,
                1L
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게임 카드 등록 시 회원 ID가 null일 때 실패한다.")
    void enrollGameCardMemberIdNullError() throws Exception {
        GameCardRequest gameCardRequest = new GameCardRequest(
                "test",
                100.0,
                null,
                1L
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게임 카드 등록 시 게임 ID가 null일 때 실패한다.")
    void enrollGameCardGameIdNullError() throws Exception {
        GameCardRequest gameCardRequest = new GameCardRequest(
                "test",
                100.0,
                1L,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/game-cards")
                        .content(OBJECT_MAPPER.writeValueAsString(gameCardRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}