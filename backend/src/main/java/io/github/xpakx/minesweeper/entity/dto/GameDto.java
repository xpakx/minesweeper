package io.github.xpakx.minesweeper.entity.dto;

import java.util.List;

public interface GameDto {
    Long getId();
    Integer getWidth();
    Integer getHeight();
    Integer getBombs();
    boolean getWon();
    boolean getLost();
    PlayerMinDto getPlayer();
    List<Pos> getPositions();
}
