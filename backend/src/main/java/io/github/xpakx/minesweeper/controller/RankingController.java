package io.github.xpakx.minesweeper.controller;

import io.github.xpakx.minesweeper.entity.dto.GameInfoDto;
import io.github.xpakx.minesweeper.entity.dto.RankedPlayerResponse;
import io.github.xpakx.minesweeper.service.RankingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RankingController {
    private final RankingService service;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankedPlayerResponse>> getRanking() {
        return new ResponseEntity<>(
                service.getRanking(),
                HttpStatus.OK
        );
    }
}
