package com.codecool.dartcc.service;

import com.codecool.dartcc.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

public class GameServiceTest {

    private GameService gameService = new GameService();

    @Test
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