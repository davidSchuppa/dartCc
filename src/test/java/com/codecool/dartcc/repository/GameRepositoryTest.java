package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

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

    @Test
    public void testFindGamesByPlayer() {
        Player p1 = Player.builder().name("John").build();
        Player p2 = Player.builder().name("Jane").build();
        Player p3 = Player.builder().name("Joe").build();


        Game testGame = Game.builder().player(p1).player(p2).build();
        Game testGame2 = Game.builder().player(p2).player(p3).build();
        Game testGame3 = Game.builder().player(p2).player(p1).build();

        p1.getGames().add(testGame);
        p2.getGames().add(testGame);
        gameRepository.saveAndFlush(testGame);
        p3.getGames().add(testGame2);
        p2.getGames().add(testGame2);
        gameRepository.saveAndFlush(testGame2);
        p1.getGames().add(testGame3);
        p2.getGames().add(testGame3);
        gameRepository.saveAndFlush(testGame3);

        List<Game> games = gameRepository.findAllByPlayersContaining(p1);

        assertThat(games.get(1)).isEqualTo(testGame3);
    }
}