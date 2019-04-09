package com.codecool.dartcc.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Player {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "players", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Game> games = new HashSet<>();
//    private String email;
//    private int gamesPlayed;
//    private int bestOfThree;
//    private int wins;
//    private int actualScore;
//    private int pointRemaining;
//    private double scorePerDart;
//    private double scorePerRound;
}
