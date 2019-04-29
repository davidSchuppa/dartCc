package com.codecool.dartcc.service;

import com.codecool.dartcc.DartCcApplication;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = DartCcApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    @Transactional
    public void testCreateGameReturnsLastGameId() throws JsonProcessingException {

        Object playerNames = new Object() {
            public final String playerOne = "Peti";
            public final String playerTwo = "Feri";
        };

        ObjectMapper mapper = new ObjectMapper();
        String namesJson = mapper.writeValueAsString(playerNames);
        long result = gameService.createGame(namesJson);

        assertThat(result).isEqualTo(1);
    }


}