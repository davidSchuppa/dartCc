package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Test
    public void smoke() {
        assertTrue(true);
    }

    @Test
    public void testSaveGame() {
        Game testGame = Game.builder()
                        .numberOfDoubles(3)
                        .build();

        gameRepository.save(testGame);

        List<Game> games = gameRepository.findAll();

        assertThat(games).hasSize(1);
    }



}