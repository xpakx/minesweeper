package io.github.xpakx.minesweeper.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.xpakx.minesweeper.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer x;
    private Integer y;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
