package com.example.spring_games.member.domain.vo;

import com.example.spring_games.member.domain.Member;

public enum MemberLevel {
    BRONZE,
    SILVER,
    GOLD,

    ;

//    public static MemberLevel calculateMemberLevel(Cards cards){
//        if (cards.isGoldLevelEligible()){
//            return GOLD;
//        }
//
//        if (cards.isSilverLevelEligible()){
//            return SILVER;
//        }
//
//        return BRONZE;
//    }

    public static MemberLevel calculateNewLevel(Member member){
        if (member.isGoldLevelEligible()){
            return MemberLevel.GOLD;
        }

        if (member.isSilverLevelEligible()){
            return MemberLevel.SILVER;
        }

        return MemberLevel.BRONZE;
    }



    public boolean isNotEqual(MemberLevel memberLevel){
        return memberLevel != this;
    }
}
