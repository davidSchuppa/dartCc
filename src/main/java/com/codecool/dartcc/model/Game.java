package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    private int round;
    private ArrayList players = new ArrayList();
    private int numberOfDoubles;
    private int numberOfTriples;
    private Player winner;
    private GameType gameType;
}