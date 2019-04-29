package com.codecool.dartcc.service;

import com.codecool.dartcc.DartCcApplication;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.PlayerRepository;
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

    @Test
    public void testSavePlayerReturnsExistingPlayerIfFound() {

        Player player = Player.builder().name("John").build();
        playerRepository.saveAndFlush(player);

        Player playerWithSameName = Player.builder().name("John").build();
        playerService.savePlayer(playerWithSameName.getName());

        Player existingPlayer = playerRepository.findPlayerByName(playerWithSameName.getName());

        assertThat(existingPlayer).isEqualTo(player);
    }


}