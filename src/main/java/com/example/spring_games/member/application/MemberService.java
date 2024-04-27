package com.example.spring_games.member.application;

import com.example.spring_games.game_card.application.GameCardService;
import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.member.application.dto.GameCardRequest;
import com.example.spring_games.member.application.dto.MemberInfoRequest;
import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import com.example.spring_games.member.exception.MemberException;
import com.example.spring_games.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberLevelNotificationService memberLevelNotificationService;
    private final GameCardService gameCardService;

    @Transactional
    public Long signup(MemberInfoRequest memberInfoRequest){
        if (memberRepository.existsByEmailValue(memberInfoRequest.memberEmail())){
            throw new MemberException.DuplicatedMemberEmailException(memberInfoRequest.memberEmail());
        }
        final Member member = memberInfoRequest.toEntity();
        final Member savedMember = memberRepository.save(member);

        memberLevelNotificationService.notifyMemberLevelChanged(member.getId(), member.getMemberName(), member.getMemberLevel());

        return savedMember.getId();
    }


    @Transactional
    public Long updateMemberInfo(Long memberId, MemberInfoRequest updateRequest){
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException.MemberNotFoundException(memberId));

        member.updateMemberInfo(new MemberName(updateRequest.memberName()),
                new MemberEmail(updateRequest.memberEmail()),
                new MemberJoinDate(updateRequest.joinDate()));

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException.MemberNotFoundException(memberId)
                );
    }

    public List<Member> findAllByMemberNameValueAndMemberLevel(String memberId, MemberLevel memberLevel){
        return memberRepository.findAllByMemberNameValueAndMemberLevel(memberId, memberLevel);
    }

    @Transactional
    public void deleteMember(Long memberId){
        memberRepository.deleteById(memberId);
    }

    public void enrollGameCard(GameCardRequest gameCardRequest){
        GameCard gameCard = gameCardService.save(gameCardRequest);
        Member member = this.findById(gameCardRequest.memberId());
        member.addCard(gameCard);

        MemberLevel prevLevel = member.getMemberLevel();
        MemberLevel updatedLevel = MemberLevel.calculateNewLevel(member);

        if (prevLevel != updatedLevel){
            member.updateMemberLevel(updatedLevel);
            memberLevelNotificationService.notifyMemberLevelChanged(member.getId(), member.getMemberName(), updatedLevel);
        }
    }
}