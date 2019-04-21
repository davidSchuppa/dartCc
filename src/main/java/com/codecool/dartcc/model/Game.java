package com.codecool.dartcc.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
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
