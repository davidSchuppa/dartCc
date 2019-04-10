package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game getGameById(long id);

    List<Game> findAllByP1OrP2Equals(Player playerOne, Player playerTwo);
}
