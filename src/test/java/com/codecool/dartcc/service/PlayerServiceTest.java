package com.codecool.dartcc.service;

import com.codecool.dartcc.DartCcApplication;
import com.codecool.dartcc.exception.PlayerUpdateException;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.PlayerRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = DartCcApplication.class)
public class PlayerServiceTest {


    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ObjectMapper mapper;

    public Object updateGameData = new Object() {
        Object game = new Object() {
            long id = 1;
            int doubles = 3;
            int triples = 8;
            int round = 2;
            String winner = "";
        };
        Object playerOne = new Object() {
            String name = "Feri";
            int wins = 2;
            int bestOfThree = 0;
            double avgPerDart = 0;
            double avgPerTurn = 0;
        };
        Object playerTwo = new Object() {
            String name = "Peti";
            int wins = 0;
            int bestOfThree = 0;
            double avgPerDart = 0;
            double avgPerTurn = 0;
        };
    };

    @Test
    public void testSavePlayerReturnsExistingPlayerIfFound() {

        Player player = Player.builder().name("John").build();
        playerRepository.saveAndFlush(player);

        Player playerWithSameName = Player.builder().name("John").build();
        playerService.savePlayer(playerWithSameName.getName());

        Player existingPlayer = playerRepository.findPlayerByName(playerWithSameName.getName());

        assertThat(existingPlayer).isEqualTo(player);
    }

    @Test
    public void testPlayersStatsUpdatedWhenGameTurnsRegistered() throws JsonProcessingException {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String updateJson = mapper.writeValueAsString(updateGameData);

        Player player = Player.builder().name("Feri")
                .bestOfThree(60)
                .avgPerDart(10)
                .avgPerRound(32)
                .wins(0)
                .build();

        Player player2 = Player.builder().name("Peti")
                .bestOfThree(20)
                .avgPerDart(10)
                .avgPerRound(32)
                .wins(0)
                .build();

        playerRepository.save(player);
        playerRepository.save(player2);

        try {
            playerService.updatePlayersStats(updateJson);
        } catch (PlayerUpdateException e) {
            e.printStackTrace();
        }

        Player updatedPlayer = playerRepository.findPlayerByName("Feri");
        assertEquals(2, updatedPlayer.getWins());

    }
}