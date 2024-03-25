package com.example.spring_games.game_card.presentation;

import com.example.spring_games.common.presentation.ControllerTest;
import com.example.spring_games.game.application.GameService;
import com.example.spring_games.game_card.application.GameCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GameCardController.class)
class GameCardControllerTest extends ControllerTest {

    @MockBean
    private GameService gameService;

    @Test
    void getGameCardPage() throws Exception {
        long memberId = 1L;

        mockMvc.perform(get("/members/" + memberId + "/game-card"))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollCard"))
                .andExpect(model().attribute("memberId", memberId))
                .andExpect(model().attributeExists("games"));
    }
}