package io.github.xpakx.minesweeper.entity.dto;

import io.github.xpakx.minesweeper.entity.Bomb;

import java.util.List;

public interface GameDto {
    Long getId();
    Integer getWidth();
    Integer getHeight();
    boolean getWon();
    boolean getLost();
    PlayerMinDto getPlayer();
    List<Pos> getPositions();
}
