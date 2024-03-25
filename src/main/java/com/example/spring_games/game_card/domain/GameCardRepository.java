package com.example.spring_games.game_card.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameCardRepository extends JpaRepository<GameCard, Long> {
    Long countGameCardByMemberId(Long memberId);

    @Query("SELECT gc FROM GameCard gc INNER JOIN FETCH gc.game g WHERE gc.member.id = :memberId")
    List<GameCard> findAllByMemberId(@Param("memberId") Long memberId);

    void deleteAllByMemberId(Long memberId);

    void deleteGameCardByIdAndMemberId(Long gameCardId, Long memberId);
}
