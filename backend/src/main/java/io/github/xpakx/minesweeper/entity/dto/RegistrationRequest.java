package io.github.xpakx.minesweeper.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrationRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String passwordRe;
}
