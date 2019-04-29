package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game getGameById(long id);

    List<Game> findAllByP1OrP2Equals(Player playerOne, Player playerTwo);

    Game findGameById(long id);
}
