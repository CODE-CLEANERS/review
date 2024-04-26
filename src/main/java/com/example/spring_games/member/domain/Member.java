package com.example.spring_games.member.domain;

import com.example.spring_games.game_card.domain.GameCard;
import com.example.spring_games.game_card.domain.vo.Price;
import com.example.spring_games.member.domain.vo.MemberEmail;
import com.example.spring_games.member.domain.vo.MemberJoinDate;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.example.spring_games.member.domain.vo.MemberName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    private static final int GOLD_LEVEL_STANDARD_COUNT = 2;
    private static final int GOLD_LEVEL_STANDARD_TYPE_COUNT = 2;
    private static final double GOLD_LEVEL_STANDARD_AMOUNT = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberName memberName;

    @Embedded
    private MemberEmail email;

    @Embedded
    private MemberJoinDate joinDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_level")
    private MemberLevel memberLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<GameCard> cardList = new ArrayList<>();

    public boolean isGoldLevelEligible(){
        if (!hasMoreThaneTwoTypeOfGame()){
            return false;
        }
        return getValidCardCount() >= 4 || hasMinimumCountOfCardsExceedingGoldLevelAmount();
    }

    public boolean isSilverLevelEligible(){
        return getValidCardCount() >= 1;
    }

    public long getValidCardCount() {
        return cardList.stream()
                .filter(GameCard::isValid)
                .count();
    }

    private boolean hasMoreThaneTwoTypeOfGame(){
        return cardList.stream()
                .map(GameCard::getGame)
                .distinct()
                .count() >= GOLD_LEVEL_STANDARD_TYPE_COUNT;
    }

    private boolean hasMinimumCountOfCardsExceedingGoldLevelAmount() {
        if (cardList.size() < GOLD_LEVEL_STANDARD_COUNT){
            return false;
        }
        return cardList.stream()
                .map(GameCard::getPrice)
                .mapToDouble(Price::getValue)
                .sum() > GOLD_LEVEL_STANDARD_AMOUNT;
    }

    public Member(MemberName memberName, MemberEmail email, MemberJoinDate memberJoinDate) {
        this.memberName = memberName;
        this.email = email;
        this.joinDate = memberJoinDate;
        this.memberLevel = MemberLevel.BRONZE;
    }

    public void updateMemberInfo(MemberName memberName, MemberEmail email, MemberJoinDate memberJoinDate){
        this.memberName = memberName;
        this.email = email;
        this.joinDate = memberJoinDate;
    }

    public void updateMemberLevel(MemberLevel memberLevel){
        this.memberLevel = memberLevel;
    }

    public void addCard(GameCard gameCard) {
        this.cardList.add(gameCard);
        gameCard.setOwner(this);
    }

    public void addCard(List<GameCard> gameCards){
        this.cardList.addAll(gameCards);
        gameCards.forEach(card -> card.setOwner(this));
    }
}