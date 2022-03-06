package io.github.xpakx.minesweeper.controller;

import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.dto.*;
import io.github.xpakx.minesweeper.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/players/{username}/games")
    public ResponseEntity<List<GameInfoDto>> getGamesByPlayerId(@PathVariable String username) {
        return new ResponseEntity<>(
                service.getGamesForPlayer(username),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/players/{username}/games")
    public ResponseEntity<Game> newGame(@Validated @RequestBody NewGameRequest request, @PathVariable String username) {
        return new ResponseEntity<>(
                service.newGame(username, request),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/players/{username}/games/{gameId}/moves")
    public ResponseEntity<MoveResponse> move(@RequestBody MoveRequest request,
                                                       @PathVariable String username,
                                                          @PathVariable Long gameId) {
        return new ResponseEntity<>(
                service.move(username, gameId, request),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/players/{username}/games/{gameId}/flags")
    public ResponseEntity<Flag> addFlag(@RequestBody MoveRequest request,
                                                       @PathVariable String username,
                                                       @PathVariable Long gameId) {
        return new ResponseEntity<>(
                service.addFlag(username, gameId, request),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/players/{username}/games/{gameId}/flags")
    public ResponseEntity<List<Flag>> getFlagsByGameId(@PathVariable String username,
                                                       @PathVariable Long gameId) {
        return new ResponseEntity<>(
                service.getFlags(username, gameId),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @DeleteMapping("/players/{username}/games/{gameId}/flags/{flagId}")
    public ResponseEntity<?> deleteFlag(@PathVariable String username,
                                        @PathVariable Long gameId,
                                        @PathVariable Long flagId) {

        service.deleteFlag(username, gameId, flagId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
