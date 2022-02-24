package io.github.xpakx.minesweeper.service;

import io.github.xpakx.minesweeper.entity.Bomb;
import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.Position;
import io.github.xpakx.minesweeper.entity.dto.*;
import io.github.xpakx.minesweeper.error.AlreadyRevealedException;
import io.github.xpakx.minesweeper.repo.BombRepository;
import io.github.xpakx.minesweeper.repo.GameRepository;
import io.github.xpakx.minesweeper.repo.PlayerRepository;
import io.github.xpakx.minesweeper.repo.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final BombRepository bombRepository;
    private final PositionRepository positionRepository;
    private final PlayerRepository playerRepository;
    private static final Integer BOMB = 9;
    private static final Integer REVEALED_BOMB = 10;

    public GameDto getGame(Long gameId) {
        return gameRepository
                .findProjectedById(gameId)
                .orElseThrow();
    }

    public List<GameInfoDto> getGamesForPlayer(String username) {
        return gameRepository
                .findProjectedByPlayerId(getIdByUsername(username));
    }

    private Long getIdByUsername(String username) {
        return playerRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not such user"))
                .getId();
    }

    @Transactional
    public Game newGame(String username, NewGameRequest request) {
        Game game = gameRepository.save(createGameFromRequest(getIdByUsername(username), request));
        bombRepository.saveAll(getRandomBombs(game));
        return game;
    }

    private Game createGameFromRequest(Long playerId, NewGameRequest request) {
        Game game = new Game();
        game.setHeight(request.getHeight());
        game.setWidth(request.getWidth());
        game.setLost(false);
        game.setWon(false);
        game.setPlayer(playerRepository.getById(playerId));
        game.setPositions(new ArrayList<>());
        return game;
    }

    private List<Bomb> getRandomBombs(Game game) {
        int bombs = (int)(Math.sqrt(game.getHeight()*game.getWidth()));
        List<Bomb> allPositions = new ArrayList<>();
        for(int i=0; i<game.getHeight();i++) {
            for(int j=0; j<game.getWidth();j++) {
                Bomb bomb = new Bomb();
                bomb.setGame(game);
                bomb.setRevealed(false);
                bomb.setX(j);
                bomb.setY(i);
                allPositions.add(bomb);
            }
        }
        Collections.shuffle(allPositions);
        return allPositions.subList(0, bombs);
    }

    @Transactional
    public List<PositionResponse> move(String username, Long gameId, MoveRequest move) {
        Game game = gameRepository
                .findByIdAndPlayerId(gameId, getIdByUsername(username))
                .orElseThrow();
        List<Bomb> bombs = bombRepository
                .findByGameId(gameId);
        testIfPositionAlreadyRevealed(move, game);
        List<Position> newPositions = new ArrayList<>();
        boolean bombDetonated = isBombAtPosition(move, bombs);
        Position newPosition = moveToPos(move, game);
        if(bombDetonated) {
            newPosition.setNumber(REVEALED_BOMB);
            newPositions.addAll(mapBombsToPositions(move, bombs, game));
        } else {
            newPosition.setNumber(getNumOfBombsAround(newPosition,bombs));
            newPositions.addAll(propagateMove(newPosition, bombs, game));
        }
        newPositions.add(newPosition);
        positionRepository.saveAll(newPositions);
        return newPositions.stream()
                .map(PositionResponse::new)
                .collect(Collectors.toList());
    }

    private boolean isBombAtPosition(MoveRequest move, List<Bomb> bombs) {
        return bombs.stream()
                .anyMatch((p) -> p.getX() == move.getX() && p.getY() == move.getY());
    }

    private List<Position> mapBombsToPositions(MoveRequest move, List<Bomb> bombs, Game game) {
        return bombs.stream()
                .filter((p) -> p.getX() != move.getX() || p.getY() != move.getY())
                .map((a) -> bombToPos(a, game))
                .collect(Collectors.toList());
    }

    private void testIfPositionAlreadyRevealed(MoveRequest move, Game game) {
        Optional<Position> testPos = game.getPositions().stream()
                .filter((p) -> p.getX() == move.getX() && p.getY()== move.getY())
                .findAny();
        if(testPos.isPresent()) {
            throw new AlreadyRevealedException("This position is already revealed!");
        }
    }

    private List<Position> propagateMove(Position move, List<Bomb> bombs, Game game) {
        List<Position> revealed = new ArrayList<>();
        if(move.getNumber()==0) {
            boolean[][] visited = new boolean[game.getWidth()][game.getHeight()];
            for(int i=0; i<game.getWidth();i++) {
                for(int j=0; j<game.getHeight();j++) {
                    visited[i][j] = false;
                }
            }
            List<Position> nextPositions = new ArrayList<>();
            nextPositions.add(move);
            visited[move.getX()][move.getY()] = true;
            while(nextPositions.size()>0) {
                List<Position> newNextPositions = new ArrayList<>();
                for(Position p : nextPositions) {
                    if(p.getNumber() == 0) {
                        if (p.getX() + 1 < game.getWidth() && !visited[p.getX() + 1][p.getY()]) {
                            newNextPositions.add(posForCoord(p.getX() + 1, p.getY(), game, bombs));
                            visited[p.getX() + 1][p.getY()] = true;
                        }
                        if (p.getX() - 1 >= 0 && !visited[p.getX() - 1][p.getY()]) {
                            newNextPositions.add(posForCoord(p.getX() - 1, p.getY(), game, bombs));
                            visited[p.getX() - 1][p.getY()] = true;
                        }
                        if (p.getY() + 1 < game.getHeight() && !visited[p.getX()][p.getY() + 1]) {
                            newNextPositions.add(posForCoord(p.getX(), p.getY() + 1, game, bombs));
                            visited[p.getX()][p.getY() + 1] = true;
                        }
                        if (p.getY() - 1 >= 0 && !visited[p.getX()][p.getY() - 1]) {
                            newNextPositions.add(posForCoord(p.getX(), p.getY() - 1, game, bombs));
                            visited[p.getX()][p.getY() - 1] = true;
                        }
                        if (p.getX() + 1 < game.getWidth() && p.getY() + 1 < game.getHeight()  && !visited[p.getX() + 1][p.getY() + 1]) {
                            newNextPositions.add(posForCoord(p.getX() + 1, p.getY(), game, bombs));
                            visited[p.getX() + 1][p.getY()] = true;
                        }
                        if (p.getX() + 1 < game.getWidth() && p.getY() - 1 >=0  && !visited[p.getX() + 1][p.getY() - 1]) {
                            newNextPositions.add(posForCoord(p.getX() + 1, p.getY(), game, bombs));
                            visited[p.getX() + 1][p.getY()] = true;
                        }

                        if (p.getX() - 1 > 0 && p.getY() + 1 < game.getHeight()  && !visited[p.getX() - 1][p.getY() + 1]) {
                            newNextPositions.add(posForCoord(p.getX() + 1, p.getY(), game, bombs));
                            visited[p.getX() + 1][p.getY()] = true;
                        }
                        if (p.getX() - 1 > 0 && p.getY() - 1 >=0  && !visited[p.getX() - 1][p.getY() - 1]) {
                            newNextPositions.add(posForCoord(p.getX() + 1, p.getY(), game, bombs));
                            visited[p.getX() + 1][p.getY()] = true;
                        }
                    }
                }
                nextPositions = newNextPositions;
                revealed.addAll(nextPositions);
            }
        }
        positionRepository.saveAll(revealed);
        return revealed;
    }

    private Position posForCoord(int x, int y, Game game, List<Bomb> bombs) {
        Position pos = new Position();
        pos.setGame(game);
        pos.setX(x);
        pos.setY(y);
        pos.setNumber(getNumOfBombsAround(pos, bombs));
        return pos;
    }

    private Position moveToPos(MoveRequest move, Game game) {
        Position pos = new Position();
        pos.setGame(game);
        pos.setX(move.getX());
        pos.setY(move.getY());
        return pos;
    }

    private Position bombToPos(Bomb bomb, Game game) {
        Position pos = new Position();
        pos.setGame(game);
        pos.setX(bomb.getX());
        pos.setY(bomb.getY());
        pos.setNumber(BOMB);
        return pos;
    }

    private Integer getNumOfBombsAround(Position pos, List<Bomb> bombs) {
        return Math.toIntExact(
                bombs.stream()
                    .filter((b) -> b.getX()>=pos.getX()-1 && b.getX()<=pos.getX()+1)
                    .filter((b) -> b.getY()>=pos.getY()-1 && b.getY()<=pos.getY()+1)
                    .count()
        );
    }
}
