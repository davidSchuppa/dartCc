package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(appliesTo = "game")
public  class Game {
    @Id
    @GeneratedValue
    private long id;
    private int round;
    @ManyToOne
    private Player playerOne;
    @ManyToOne
    private Player playerTwo;
    private int numberOfDoubles;
//    private int numberOfTriples;
//    private Player winner;
//    private GameType gameType;
}
