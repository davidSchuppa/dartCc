package com.codecool.dartcc.service;

import com.codecool.dartcc.exception.GameNotFoundException;
import com.codecool.dartcc.exception.PlayerUpdateException;
import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.GameUpdate;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.GameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObjectMapper mapper;


    public long createGame(String playerNames) {
        long gameId = 0;
        try {
            Map<String, String> names = mapper.readValue(playerNames, Map.class);
            String playerOne = names.get("playerOne");
            String playerTwo = names.get("playerTwo");

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

    public void updateGame(String gameJson) throws GameNotFoundException {
        try {
            playerService.updatePlayersStats(gameJson);
            JsonNode gameData = mapper.readTree(gameJson).get("game");
            GameUpdate updateData= mapper.treeToValue(gameData, GameUpdate.class);

            Game gameToUpdate = gameRepository.findGameById(updateData.getId());
            if (gameToUpdate == null) {
                throw new GameNotFoundException("No game found with the given id");
            }
            gameToUpdate.setRound(updateData.getRound());
            gameToUpdate.setNumberOfDoubles(updateData.getDoubles());
            gameToUpdate.setNumberOfTriples(updateData.getTriples());
            gameRepository.saveAndFlush(gameToUpdate);

        } catch (IOException | PlayerUpdateException e) {
            e.printStackTrace();
        }

    }

    public String getHintFor3Dart(int score) {

        return null;
    }
}
