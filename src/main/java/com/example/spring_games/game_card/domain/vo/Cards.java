package com.example.spring_games.game_card.domain.vo;

import com.example.spring_games.game_card.domain.GameCard;

import java.util.List;

public record Cards(List<GameCard> cardList) {
    private static final int GOLD_LEVEL_STANDARD_COUNT = 2;
    private static final int GOLD_LEVEL_STANDARD_TYPE_COUNT = 2;
    private static final double GOLD_LEVEL_STANDARD_AMOUNT = 100;

    public long getValidCardCount() {
        return cardList.stream()
                .filter(GameCard::isValid)
                .count();
    }

    public double getTotalAmount() {
        return cardList.stream()
                .map(GameCard::getPrice)
                .mapToDouble(Price::getValue)
                .sum();
    }

    public boolean isGoldLevelEligible(){
        if (!hasMoreThaneTwoTypeOfGame()){
            return false;
        }
        return getValidCardCount() >= 4 || hasMinimumCountOfCardsExceedingGoldLevelAmount();
    }

    public boolean isSilverLevelEligible(){
        return getValidCardCount() >= 1;
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
}