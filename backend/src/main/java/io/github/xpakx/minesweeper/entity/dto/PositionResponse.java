package io.github.xpakx.minesweeper.entity.dto;

import io.github.xpakx.minesweeper.entity.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionResponse {
    Integer x;
    Integer y;
    Integer number;

    public PositionResponse(Position pos) {
        x = pos.getX();
        y = pos.getY();
        number = pos.getNumber();
    }
}
