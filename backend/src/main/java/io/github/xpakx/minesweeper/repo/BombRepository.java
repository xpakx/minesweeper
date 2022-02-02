package io.github.xpakx.minesweeper.repo;

import io.github.xpakx.minesweeper.entity.Bomb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BombRepository  extends JpaRepository<Bomb, Long> {
    List<Bomb> findByGameId(Long gameId);
}