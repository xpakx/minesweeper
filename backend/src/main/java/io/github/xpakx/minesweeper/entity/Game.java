package io.github.xpakx.minesweeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "game-with-pos",
        attributeNodes = {@NamedAttributeNode("positions"), @NamedAttributeNode("player")}
)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer width;
    private Integer height;
    private Integer bombs;
    private boolean won;
    private boolean lost;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @JsonIgnore
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<Position> positions;

    private LocalDateTime completedAt;
}
