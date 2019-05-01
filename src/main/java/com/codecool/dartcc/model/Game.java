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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Player p1;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Player p2;

    private int numberOfDoubles;
    private int numberOfTriples;

    @OneToOne
    private Player winner;
}
