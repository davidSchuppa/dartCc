package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
