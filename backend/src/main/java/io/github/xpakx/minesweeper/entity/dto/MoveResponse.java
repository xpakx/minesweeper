package io.github.xpakx.minesweeper.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoveResponse {
    List<PositionResponse> positions;
    boolean won;
    boolean lost;
}
