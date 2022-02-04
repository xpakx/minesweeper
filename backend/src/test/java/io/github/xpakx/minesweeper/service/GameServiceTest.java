package io.github.xpakx.minesweeper.service;

import io.github.xpakx.minesweeper.entity.Bomb;
import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.dto.MoveRequest;
import io.github.xpakx.minesweeper.entity.dto.PositionResponse;
import io.github.xpakx.minesweeper.repo.BombRepository;
import io.github.xpakx.minesweeper.repo.GameRepository;
import io.github.xpakx.minesweeper.repo.PlayerRepository;
import io.github.xpakx.minesweeper.repo.PositionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private BombRepository bombRepository;
    @Mock
    private PlayerRepository playerRepository;

    private GameService service;

    private Game fiveXfourGame;
    private List<Bomb> oneBomb;

    @BeforeEach
    void setUp() {
        fiveXfourGame = new Game();
        fiveXfourGame.setPositions(new ArrayList<>());
        fiveXfourGame.setLost(false);
        fiveXfourGame.setWon(false);
        fiveXfourGame.setWidth(5);
        fiveXfourGame.setHeight(4);
        Bomb newBomb = new Bomb();
        newBomb.setGame(fiveXfourGame);
        newBomb.setRevealed(false);
        newBomb.setX(2);
        newBomb.setY(1);
        oneBomb = new ArrayList<>();
        oneBomb.add(newBomb);
    }

    @AfterEach
    void tearDown() {
    }

    private void injectMocks() {
        service = new GameService(gameRepository, bombRepository, positionRepository, playerRepository);
    }

    @Test
    void shouldRespondWithOnePos() {
        given(gameRepository.findByIdAndPlayerId(any(Long.class), any(Long.class)))
                .willReturn(Optional.of(fiveXfourGame));
        given(bombRepository.findByGameId(any(Long.class)))
                .willReturn(oneBomb);
        injectMocks();
        MoveRequest oneXOne = new MoveRequest();
        oneXOne.setX(1);
        oneXOne.setY(1);

        List<PositionResponse> response = service.move(1L, 1L, oneXOne);

        for(PositionResponse p : response) {
            System.out.println(p.getX()+"x"+p.getY()+":"+p.getNumber());
        }
        assertEquals(1, response.size());
    }

    @Test
    void shouldRespondWith18Pos() {
        given(gameRepository.findByIdAndPlayerId(any(Long.class), any(Long.class)))
                .willReturn(Optional.of(fiveXfourGame));
        given(bombRepository.findByGameId(any(Long.class)))
                .willReturn(oneBomb);
        injectMocks();
        MoveRequest zeroXzero = new MoveRequest();
        zeroXzero.setX(0);
        zeroXzero.setY(0);

        List<PositionResponse> response = service.move(1L, 1L, zeroXzero);

        for(PositionResponse p : response) {
            System.out.println(p.getX()+"x"+p.getY()+":"+p.getNumber());
        }
        assertEquals(18, response.size());
    }
}