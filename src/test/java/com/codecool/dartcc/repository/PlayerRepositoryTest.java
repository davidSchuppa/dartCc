package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    @Test
    public void testSavePlayer() {
        Player p1 = Player.builder().name("Feri").build();

        playerRepository.saveAndFlush(p1);

        List<Player> players = playerRepository.findAll();

        assertThat(players).hasSize(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testPlayerNamesAreUnique() {
        Player p1 = Player.builder().name("Feri").build();
        Player p2 = Player.builder().name("Feri").build();

        playerRepository.saveAndFlush(p1);
        playerRepository.saveAndFlush(p2);
    }

    @Test
    public void testFindPlayerByName() {
        Player p1 = Player.builder().name("Feri").build();
        Player p2 = Player.builder().name("Peti").build();
        Player p3 = Player.builder().name("Jani").build();

        List<Player> players = Arrays.asList(p1, p2, p3);

        playerRepository.saveAll(players);

        Player foundPlayer = playerRepository.findPlayerByName("Peti");
        assertThat(foundPlayer).isEqualTo(p2);
    }
}