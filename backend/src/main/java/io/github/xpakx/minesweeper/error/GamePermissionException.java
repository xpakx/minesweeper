package io.github.xpakx.minesweeper.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class GamePermissionException extends RuntimeException {
    public GamePermissionException(String message) {
        super(message);
    }
}
