package com.example.spring_games.game.domain;

import com.example.spring_games.game.domain.vo.GameName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Game {

    private static final Long ONE_HUNDRED_MILLION = 1_000_000_00L;
    private static final int RANDOM_NUMBER_BOUND = 100_000_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GameName gameName;

    public Game(GameName gameName) {
        this.gameName = gameName;
    }

    public Long makeSerialNumber(){
        int randomNumber = new Random().nextInt(RANDOM_NUMBER_BOUND);
        return id * ONE_HUNDRED_MILLION + randomNumber;
    }
}