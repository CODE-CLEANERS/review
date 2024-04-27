package com.example.spring_games.member.presentaion;

import com.example.spring_games.member.application.dto.MemberInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/members/signup")
    public String signup(Model model, @ModelAttribute MemberInfoRequest memberInfoRequest){
        model.addAttribute("memberInfoRequest", memberInfoRequest);
        return "signupForm";
    }
}
