package com.example.spring_games.member.domain.vo;

import com.example.spring_games.game_card.domain.vo.Cards;

public enum MemberLevel {
    BRONZE,
    SILVER,
    GOLD,

    ;

    public static MemberLevel calculateMemberLevel(Cards cards){
        if (cards.isGoldLevelEligible()){
            return GOLD;
        }

        if (cards.isSilverLevelEligible()){
            return SILVER;
        }

        return BRONZE;
    }

    public boolean isNotEqual(MemberLevel memberLevel){
        return memberLevel != this;
    }
}
