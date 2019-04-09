package com.codecool.dartcc.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;
    private int round;
    private int numberOfDoubles;

    @OneToOne(cascade = CascadeType.ALL)
    private Player p1;
    @OneToOne(cascade = CascadeType.ALL)
    private Player p2;
//    private int numberOfTriples;
//    private Player winner;
//    private GameType gameType;
}
