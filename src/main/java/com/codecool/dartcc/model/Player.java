package com.codecool.dartcc.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Player {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    private int gamesPlayed;
    private int bestOfThree;
    private int wins;
    private int actualScore;
    private int pointRemaining;
    private double scorePerDart;
    private double scorePerRound;
}
