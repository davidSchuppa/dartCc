package com.codecool.dartcc.controller;

import com.codecool.dartcc.exception.NoCheckoutFoundException;
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
        public Player playerOne = Player.builder().name("Peti").wins(0).bestOfThree(60).avgPerDart(20.1).avgPerRound(32.2).build();
        public Player playerTwo = Player.builder().name("Feri").wins(1).bestOfThree(30).avgPerDart(15.5).avgPerRound(10.3).build();
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

    @Test
    public void testGetHintFor2DartRespondsWithHint() throws Exception {

        try {
            given(mockGameService.getHintFor2Dart(40)).willReturn("D20");
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }

        mvc.perform(get("/hint-2/40"))
                .andExpect(status().isOk())
                .andExpect(content().string("D20"));
    }

    @Test
    public void testGetHintFor2DartRespondsWithErrorMessageIfCheckoutNotFound() throws Exception {

        try {
            given(mockGameService.getHintFor2Dart(170)).willThrow(NoCheckoutFoundException.class);
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }

        mvc.perform(get("/hint-2/170"))
                .andExpect(status().isOk())
                .andExpect(content().string("No possible checkout hint for 2 darts"));
    }

    @Test
    public void testGetHintFor3DartRespondsWithHint() throws Exception {

        try {
            given(mockGameService.getHintFor3Dart(170)).willReturn("T20 T20 Bull");
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }

        mvc.perform(get("/hint-3/170"))
                .andExpect(status().isOk())
                .andExpect(content().string("T20 T20 Bull"));
    }

    @Test
    public void testGetHintFor3DartRespondsWithErrorMessageIfCheckoutNotFound() throws Exception {

        try {
            given(mockGameService.getHintFor3Dart(190)).willThrow(NoCheckoutFoundException.class);
        } catch (NoCheckoutFoundException e) {
            e.printStackTrace();
        }

        mvc.perform(get("/hint-3/190"))
                .andExpect(status().isOk())
                .andExpect(content().string("No possible checkout hint for 3 darts"));
    }
}