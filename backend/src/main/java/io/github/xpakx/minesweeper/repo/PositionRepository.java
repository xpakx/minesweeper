package io.github.xpakx.minesweeper.repo;

import io.github.xpakx.minesweeper.entity.Player;
import io.github.xpakx.minesweeper.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository  extends JpaRepository<Player, Long> {
    List<Position> findByGameId(Long gameId);
}
