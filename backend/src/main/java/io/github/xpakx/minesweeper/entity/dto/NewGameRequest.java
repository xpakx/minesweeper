package io.github.xpakx.minesweeper.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewGameRequest {
    int width;
    int height;
}
