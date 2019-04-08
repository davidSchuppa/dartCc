package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String name;
    private long id;
    private String email;
    private int gamesPlayed;
    private int bestOfThree;
    private int wins;
    private int actualScore;
    private int pointRemaining;
    private double scorePerDart;
    private double scorePerRound;


}
