package io.github.xpakx.minesweeper.controller;

import io.github.xpakx.minesweeper.entity.dto.AuthenticationRequest;
import io.github.xpakx.minesweeper.entity.dto.AuthenticationResponse;
import io.github.xpakx.minesweeper.entity.dto.GameDto;
import io.github.xpakx.minesweeper.entity.dto.GameInfoDto;
import io.github.xpakx.minesweeper.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class GameController {
    private final GameService service;

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long gameId) {
        return new ResponseEntity<>(
                service.getGame(gameId),
                HttpStatus.OK
        );
    }

    @GetMapping("/players/{playerId}/games")
    public ResponseEntity<List<GameInfoDto>> getGamesByPlayerId(@PathVariable Long playerId) {
        return new ResponseEntity<>(
                service.getGamesForPlayer(playerId),
                HttpStatus.OK
        );
    }
}
