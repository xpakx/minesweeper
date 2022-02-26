package io.github.xpakx.minesweeper.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankedPlayerResponse {
    Long id;
    String username;
    Long won;
    Long lost;
}
