package io.github.xpakx.minesweeper.repo;

import io.github.xpakx.minesweeper.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
    List<Game> findByPlayerId(Long playerId);
}
