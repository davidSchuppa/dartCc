package com.codecool.dartcc.controller;

import com.codecool.dartcc.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WebController.class)
public class WebControllerTest {
    @Autowired
    private MockMvc mvc;

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

        ObjectMapper mapper = new ObjectMapper();
        String reqBody = mapper.writeValueAsString(playerNames);

        mvc.perform(post("/create-game")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(reqBody)
        )
                .andExpect(status().isOk());
    }
}