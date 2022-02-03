package io.github.xpakx.minesweeper.service;

import io.github.xpakx.minesweeper.entity.dto.GameDto;
import io.github.xpakx.minesweeper.entity.dto.GameInfoDto;
import io.github.xpakx.minesweeper.repo.BombRepository;
import io.github.xpakx.minesweeper.repo.GameRepository;
import io.github.xpakx.minesweeper.repo.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final BombRepository bombRepository;
    private final PositionRepository positionRepository;

    public GameDto getGame(Long gameId) {
        return gameRepository
                .findProjectedById(gameId)
                .orElseThrow();
    }

    public List<GameInfoDto> getGamesForPlayer(Long playerId) {
        return gameRepository
                .findProjectedByPlayerId(playerId);
    }
}
