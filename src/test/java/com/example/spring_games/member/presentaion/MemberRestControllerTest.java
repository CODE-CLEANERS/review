package com.example.spring_games.member.presentaion;

import com.example.spring_games.member.application.MemberService;
import com.example.spring_games.member.application.dto.MemberInfoRequest;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberRestController.class)
class MemberRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    @DisplayName("회원 가입에 성공한다.")
    public void signupSuccess() throws Exception {
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest(
                "testUser",
                "test@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        when(memberService.signup(any(MemberInfoRequest.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(memberInfoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/members/1"));
    }

    @Test
    @DisplayName("회원 정보 수정을 성공한다.")
    public void updateMemberInfoSuccess() throws Exception {
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest(
                "updatedUser",
                "updated@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        when(memberService.updateMemberInfo(eq(1L), any(MemberInfoRequest.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/1")
                        .content(OBJECT_MAPPER.writeValueAsString(memberInfoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/members/1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", " "})
    @DisplayName("회원 이름이 빈 값이면 회원 가입에 실패한다.")
    public void signupBlankMemberName(String emptyValue) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                emptyValue,
                "test@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 이름이 2자 미만일 때 회원 가입에 실패한다.")
    public void signupInvalidSizeMemberNameTooSmall() throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "a",
                "test@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 이름이 100자를 초과할 때 회원 가입에 실패한다.")
    public void signupInvalidSizeMemberNameTooLong() throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "a".repeat(101),
                "test@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test ", "t.st", "?test", "/test"})
    @DisplayName("회원 이름이 한글이나 영문으로 구성되지 않을 때 회원 가입에 실패한다.")
    public void signupInvalidPatternMemberName(String invalidInput) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                invalidInput,
                "test@test.com",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", " "})
    @DisplayName("회원 이메일이 빈 값이면 회원 가입에 실패한다.")
    public void signupBlankMemberEmail(String emptyValue) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "testUser",
                emptyValue,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "invalid.email", "@invalid", "invalid@email", "invalid@email.too-long-domain", "invalid@email.x"})
    @DisplayName("회원 이메일이 올바르지 않은 형식이면 회원 가입에 실패한다.")
    public void signupInvalidFormatMemberEmail(String invalidEmail) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "testUser",
                invalidEmail,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   ", " "})
    @DisplayName("회원 가입 날짜가 빈 값이면 회원 가입에 실패한다.")
    public void signupBlankJoinDate(String emptyValue) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "testUser",
                "test@test.com",
                emptyValue
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"20231101", "2023-1101", "2024-11", "2023:11:01", "invalid"})
    @DisplayName("회원 가입 날짜가 잘못된 형식이면 회원 가입에 실패한다.")
    public void signupInvalidFormatJoinDate(String invalidDate) throws Exception {
        MemberInfoRequest invalidRequest = new MemberInfoRequest(
                "testUser",
                "test@test.com",
                invalidDate
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(OBJECT_MAPPER.writeValueAsString(invalidRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}