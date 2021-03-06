package io.github.xpakx.minesweeper.service;

import io.github.xpakx.minesweeper.entity.Bomb;
import io.github.xpakx.minesweeper.entity.Game;
import io.github.xpakx.minesweeper.entity.Player;
import io.github.xpakx.minesweeper.entity.dto.MoveRequest;
import io.github.xpakx.minesweeper.entity.dto.PositionResponse;
import io.github.xpakx.minesweeper.repo.*;
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
    private FlagRepository flagRepository;
    @Mock
    private PlayerRepository playerRepository;

    private GameService service;

    private Game fiveXfourGame;
    private List<Bomb> oneBomb;
    private Player user1;

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
        user1 = new Player();
        user1.setId(1L);
    }

    @AfterEach
    void tearDown() {
    }

    private void injectMocks() {
        service = new GameService(gameRepository, bombRepository, flagRepository, positionRepository, playerRepository);
    }

    @Test
    void shouldRespondWithOnePos() {
        given(gameRepository.findByIdAndPlayerId(any(Long.class), any(Long.class)))
                .willReturn(Optional.of(fiveXfourGame));
        given(bombRepository.findByGameId(any(Long.class)))
                .willReturn(oneBomb);
        given(playerRepository.findByUsername(any(String.class)))
                .willReturn(Optional.of(user1));
        injectMocks();
        MoveRequest oneXOne = new MoveRequest();
        oneXOne.setX(1);
        oneXOne.setY(1);

        List<PositionResponse> response = service.move("user1", 1L, oneXOne).getPositions();

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
        given(playerRepository.findByUsername(any(String.class)))
                .willReturn(Optional.of(user1));
        injectMocks();
        MoveRequest zeroXzero = new MoveRequest();
        zeroXzero.setX(0);
        zeroXzero.setY(0);

        List<PositionResponse> response = service.move("user1", 1L, zeroXzero).getPositions();

        for(PositionResponse p : response) {
            System.out.println(p.getX()+"x"+p.getY()+":"+p.getNumber());
        }
        assertEquals(16, response.size());
    }
}