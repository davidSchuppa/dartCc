package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdate {

    long id;
    int round;
    int doubles;
    int triples;
    String winner;

}
