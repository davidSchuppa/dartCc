package com.codecool.dartcc.controller;

import com.codecool.dartcc.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void testIndexMethod() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk()).andExpect(content().string("index"));
    }

    @Test
    public void testCreateGameReturnsOkWhenValidBody() throws Exception {
        Object playerNames = new Object() {
            public final String playerOne = "Peti";
            public final String playerTwo = "Feri";
        };

        String reqBody = mapper.writeValueAsString(playerNames);

        mvc.perform(post("/create-game")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(reqBody)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateGameRespondsWithGameId() throws Exception {
        Object playerNames = new Object() {
            public final String playerOne = "Peti";
            public final String playerTwo = "Feri";
        };

        String reqBody = mapper.writeValueAsString(playerNames);

        given(mockGameService.createGame(reqBody)).willReturn(1L);

        mvc.perform(post("/create-game")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(reqBody)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("1"));


    }
}