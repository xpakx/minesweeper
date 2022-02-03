package io.github.xpakx.minesweeper.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyRevealedException extends RuntimeException {
    public AlreadyRevealedException(String message) {
        super(message);
    }
}
