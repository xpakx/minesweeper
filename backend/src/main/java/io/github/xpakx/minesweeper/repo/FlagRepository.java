package io.github.xpakx.minesweeper.repo;

import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.dto.Flag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlagRepository extends JpaRepository<Flag, Long> {
    List<Flag> findByGameId(Long gameId);
}
