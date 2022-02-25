package io.github.xpakx.minesweeper.repo;

import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.dto.GameDto;
import io.github.xpakx.minesweeper.entity.dto.GameInfoDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
    List<Game> findByPlayerId(Long playerId);
    List<GameInfoDto> findProjectedByPlayerId(Long playerId);
    @EntityGraph("game-with-pos") Optional<GameDto> findProjectedById(Long id);
    @EntityGraph("game-with-pos") Optional<Game> findByIdAndPlayerId(Long id, Long playerId);
    boolean existsByIdAndPlayerId(Long id, Long playerId);
}
