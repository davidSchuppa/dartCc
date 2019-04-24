package com.codecool.dartcc.service;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;


    public long createGame(String playerNames) {
        long gameId = 0;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerOne = String.valueOf(mapper.readTree(playerNames).get("playerOne"));
            String playerTwo = String.valueOf(mapper.readTree(playerNames).get("playerTwo"));

            Player p1 = playerService.savePlayer(playerOne);
            Player p2 = playerService.savePlayer(playerTwo);

            Game newGame = Game.builder().p1(p1).p2(p2).build();
            gameRepository.saveAndFlush(newGame);
            gameId = newGame.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameId;
    }
}
