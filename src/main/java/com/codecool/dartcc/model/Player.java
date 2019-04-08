package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player {
    @Id
    @GeneratedValue
    private long id;
    private String name;
//    private String email;
//    private int gamesPlayed;
//    private int bestOfThree;
//    private int wins;
//    private int actualScore;
//    private int pointRemaining;
//    private double scorePerDart;
//    private double scorePerRound;


}
