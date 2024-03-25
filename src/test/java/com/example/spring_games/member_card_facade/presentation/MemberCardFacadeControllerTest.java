package com.example.spring_games.member_card_facade.presentation;

import com.example.spring_games.common.presentation.ControllerTest;
import com.example.spring_games.member.application.dto.MemberSearchRequest;
import com.example.spring_games.member_card_facade.application.MemberCardFacadeService;
import com.example.spring_games.member_card_facade.application.dto.MemberCardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MemberCardFacadeController.class)
class MemberCardFacadeControllerTest extends ControllerTest {

    @MockBean
    private MemberCardFacadeService memberCardFacadeService;

    @Test
    void getMultipleMembersWithCardCounts() throws Exception {
        MemberSearchRequest searchRequest = new MemberSearchRequest();
        mockMvc.perform(get("/members")
                        .flashAttr("memberSearchRequest", searchRequest))
                .andExpect(status().isOk())
                .andExpect(view().name("membersList"))
                .andExpect(model().attributeExists("members", "memberSearchRequest", "memberLevels"));
    }


    @Test
    void memberDetail() throws Exception {
        long memberId = 1L;

        MemberCardResponse memberCard = mock(MemberCardResponse.class);
        when(memberCardFacadeService.getSingleMemberWithCardCount(memberId))
                .thenReturn(memberCard);

        mockMvc.perform(get("/members/" + memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("member_detail"))
                .andExpect(model().attribute("member", memberCard))
                .andExpect(model().attributeExists( "memberCards"));
    }



    @Test
    void memberInfoEdit() throws Exception {
        long memberId = 1L;

        MemberCardResponse prevMemberInfo = mock(MemberCardResponse.class);
        when(memberCardFacadeService.getSingleMemberWithCardCount(memberId))
                .thenReturn(prevMemberInfo);

        mockMvc.perform(get("/members/" + memberId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateForm"))
                .andExpect(model().attribute("prevMemberInfo", prevMemberInfo));
    }
}