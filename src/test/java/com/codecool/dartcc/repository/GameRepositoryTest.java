package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
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


        Game testGame = Game.builder().p1(p1).p2(p2).build();
        Game testGame2 = Game.builder().p1(p2).p2(p3).build();
        Game testGame3 = Game.builder().p1(p2).p2(p1).build();

        gameRepository.saveAndFlush(testGame);
        gameRepository.saveAndFlush(testGame2);
        gameRepository.saveAndFlush(testGame3);

        List<Game> games = gameRepository.findAllByP1OrP2Equals(p1, p1);
        assertThat(games).hasSize(2);
    }

    @Test
    public void testUpdateGame() {
        Player p1 = Player.builder().name("John").build();
        Player p2 = Player.builder().name("Jane").build();

        Game testGame = Game.builder().p1(p1).p2(p2).build();

        gameRepository.saveAndFlush(testGame);

        testGame.setNumberOfDoubles(5);
        gameRepository.save(testGame);
        Game game = gameRepository.getGameById(1);
        assertThat(game.getNumberOfDoubles()).isEqualTo(5);
    }
}