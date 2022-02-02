package io.github.xpakx.minesweeper.controller;

import io.github.xpakx.minesweeper.entity.dto.AuthenticationRequest;
import io.github.xpakx.minesweeper.entity.dto.AuthenticationResponse;
import io.github.xpakx.minesweeper.entity.dto.RegistrationRequest;
import io.github.xpakx.minesweeper.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(
                authenticationService.generateAuthenticationToken(authenticationRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(
                authenticationService.register(registrationRequest),
                HttpStatus.CREATED
        );
    }
}
