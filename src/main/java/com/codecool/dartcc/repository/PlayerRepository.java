package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findPlayerByName(String name);
}
