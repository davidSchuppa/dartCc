package com.codecool.dartcc.service;

import com.codecool.dartcc.DartCcApplication;
import com.codecool.dartcc.exception.GameNotFoundException;
import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.repository.GameRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = DartCcApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private GameRepository gameRepository;

    public Object updateGameData = new Object() {
        Object game = new Object() {
            long id = 1;
            int doubles = 3;
            int triples = 8;
            int round = 2;
            String winner = "";
        };
        Object playerOne = new Object() {
            String name = "Peti";
            int wins = 0;
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

        Object playerNames = new Object() {
            public final String playerOne = "Peti";
            public final String playerTwo = "Feri";
        };

    @Test
    @Transactional
    public void testCreateGameReturnsLastGameId() throws JsonProcessingException {

        String namesJson = mapper.writeValueAsString(playerNames);
        long result = gameService.createGame(namesJson);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testUpdateGameUpdatesGameDataFromJson() throws JsonProcessingException {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String gameJson = mapper.writeValueAsString(updateGameData);

        Game game = Game.builder()

                .round(1)
                .numberOfTriples(4)
                .numberOfDoubles(2)
                .build();

        gameRepository.save(game);

        try {
            gameService.updateGame(gameJson);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }

        Game updatedGame = gameRepository.findGameById(1);
        assertEquals(8, updatedGame.getNumberOfTriples());
        assertEquals(2, updatedGame.getRound());
    }


}