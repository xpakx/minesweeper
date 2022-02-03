package io.github.xpakx.minesweeper.entity.dto;

public interface GameInfoDto {
    Long getId();
    Integer getWidth();
    Integer getHeight();
    boolean getWon();
    boolean getLost();
}
