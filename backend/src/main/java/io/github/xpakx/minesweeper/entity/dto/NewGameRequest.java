package io.github.xpakx.minesweeper.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class NewGameRequest {
    @Min(value = 5, message = "Width cannot be shorter than 5!")
    @Max(value = 20, message = "Width cannot be longer than 20!")
    int width;
    @Min(value = 5, message = "Height cannot be shorter than 5!")
    @Max(value = 20, message = "Height cannot be longer than 20!")
    int height;
}
