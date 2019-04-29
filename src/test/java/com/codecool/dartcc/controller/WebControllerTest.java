package com.codecool.dartcc.controller;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.apache.bcel.util.Play;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WebController.class)
@ActiveProfiles("test")
public class WebControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private GameService mockGameService;

    private Object playerNames = new Object() {
        public final String playerOne = "Peti";
        public final String playerTwo = "Feri";
    };

    private Object turnRequest = new Object() {
        public Player playerOne = Player.builder().name("Peti").wins(0).bestOfThree(60).scorePerDart(20.1).scorePerRound(32.2).build();
        public Player playerTwo = Player.builder().name("Feri").wins(1).bestOfThree(30).scorePerDart(15.5).scorePerRound(10.3).build();
        public Game game = Game.builder()
                .p1(playerOne)
                .p2(playerTwo)
                .numberOfDoubles(3)
                .numberOfTriples(5)
                .round(1)
                .build();
    };

    @Test
    public void testIndexMethod() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk()).andExpect(content().string("index"));
    }

    @Test
    public void testCreateGameReturnsOkWhenValidBody() throws Exception {
        String reqBody = mapper.writeValueAsString(playerNames);

        mvc.perform(post("/create-game")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(reqBody)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateGameRespondsWithGameId() throws Exception {
        String reqBody = mapper.writeValueAsString(playerNames);

        given(mockGameService.createGame(reqBody)).willReturn(1L);

        mvc.perform(post("/create-game")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(reqBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testTurnHttpRequest() throws Exception {
        String reqBody = mapper.writeValueAsString(turnRequest);

        mvc.perform(put("/turn")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andExpect(status().isOk());
    }
}