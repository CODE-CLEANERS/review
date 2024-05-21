package com.example.spring_games.member.presentaion;

import com.example.spring_games.member.application.MemberManagementService;
import com.example.spring_games.member.application.dto.MemberInfoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberRestController {
    private final MemberManagementService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody MemberInfoRequest memberInfoRequest){
        final Long memberId = memberService.signup(memberInfoRequest);
        final URI uri = URI.create("/members/" + memberId);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<Void> updateMemberInfo(@PathVariable Long memberId, @RequestBody MemberInfoRequest updateRequest){
        final Long updatedId = memberService.updateMemberInfo(memberId, updateRequest);
        final URI uri = URI.create("/members/" + updatedId);
        return ResponseEntity.created(uri).build();
    }
}