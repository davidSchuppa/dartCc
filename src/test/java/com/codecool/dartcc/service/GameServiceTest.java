package com.codecool.dartcc.service;

import com.codecool.dartcc.DartCcApplication;
import com.codecool.dartcc.exception.GameNotFoundException;
import com.codecool.dartcc.exception.NoCheckoutFoundException;
import com.codecool.dartcc.model.CheckoutFor2Dart;
import com.codecool.dartcc.model.CheckoutFor3Dart;
import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.GameRepository;
import com.codecool.dartcc.repository.HintFor2Repository;
import com.codecool.dartcc.repository.HintFor3Repository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

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

    @MockBean
    private HintFor2Repository mockHintFor2Repository;

    @MockBean
    private HintFor3Repository mockHintFor3Repository;

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
                .p1(Player.builder().name("Peti").build())
                .p2(Player.builder().name("Feri").build())
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

    @Test
    public void testGetHintFor2Dart() {
        given(mockHintFor2Repository.findCheckoutFor2DartsByScore(40))
                .willReturn(CheckoutFor2Dart.builder().score(40).checkout("D20").build());

        try {
            assertEquals("D20", gameService.getHintFor2Dart(40));
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHintFor2DartThrowsExceptionWhenScoreIsNotInDB() {
        given(mockHintFor2Repository.findCheckoutFor2DartsByScore(130))
                .willReturn(null);

        try {
            gameService.getHintFor2Dart(130);
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
            String message = "No possible 2 dart checkout for score: 130";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testGetHintFor3Dart() {
        given(mockHintFor3Repository.findCheckoutFor3DartsByScore(170))
                .willReturn(CheckoutFor3Dart.builder().score(170).checkout("T20 T20 Bull").build());

        try {
            assertEquals("T20 T20 Bull", gameService.getHintFor3Dart(170));
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHintFor3DartThrowsExceptionWhenScoreIsNotInDB() {
        given(mockHintFor3Repository.findCheckoutFor3DartsByScore(200))
                .willReturn(null);

        try {
            gameService.getHintFor3Dart(200);
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
            String message = "No possible 3 dart checkout for score: 200";
            assertEquals(message, e.getMessage());
        }
    }
}