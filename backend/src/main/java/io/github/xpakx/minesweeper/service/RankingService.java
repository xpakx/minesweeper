package io.github.xpakx.minesweeper.service;

import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.dto.RankedPlayerResponse;
import io.github.xpakx.minesweeper.repo.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RankingService {
    private final GameRepository gameRepository;

    public List<RankedPlayerResponse> getRanking() {
        LocalDateTime date = LocalDateTime.now().minusDays(7);
        Map<Long, List<Game>> gamesGroupedByPlayer = gameRepository
                .findByCompletedAtAfter(date).stream()
                .collect(Collectors.groupingBy((a) -> a.getPlayer().getId()));
        List<RankedPlayerResponse> result = new ArrayList<>();
        for(Long playerId : gamesGroupedByPlayer.keySet()) {
            RankedPlayerResponse player = new RankedPlayerResponse();
            player.setId(playerId);
            List<Game> playerGames = gamesGroupedByPlayer.get(playerId);
            player.setLost(playerGames.stream().filter(Game::isLost).count());
            player.setWon(playerGames.stream().filter(Game::isWon).count());
            player.setUsername(playerGames.get(0).getPlayer().getUsername());
            result.add(player);
        }
        result.sort(this::compare);
        return result;
    }

    private int compare(RankedPlayerResponse a, RankedPlayerResponse b) {
        double playerARatio =  a.getWon() / (a.getWon() + a.getLost());
        double playerBRatio = b.getWon() / (b.getWon() + b.getLost());
        return Double.compare(playerARatio, playerBRatio);
    }
}
