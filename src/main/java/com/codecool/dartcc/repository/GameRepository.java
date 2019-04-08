package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {


}
