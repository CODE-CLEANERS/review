package com.example.spring_games.game_card.domain;

import com.example.spring_games.game.domain.Game;
import com.example.spring_games.game_card.domain.vo.Price;
import com.example.spring_games.game_card.domain.vo.Title;
import com.example.spring_games.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"game_id", "serial_number"})
)
@Getter
@EqualsAndHashCode
public class GameCard {

    private static final double FREE_CARD_VALUE = 0;

    // 고유한 아이디로 구분되고 ...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    @Getter
    private Price price;

    // TODO : 고유 번호 해결해야 한다
    @Column(name = "serial_number")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public boolean isValid(){
        return this.price.getValue() > FREE_CARD_VALUE;
    }

    public GameCard(Title title, Price price, Long serialNumber, Game game) {
        this.title = title;
        this.price = price;
        this.serialNumber = serialNumber;
        this.game = game;
    }

    public void setOwner(Member member) {
        this.member = member;
    }
}