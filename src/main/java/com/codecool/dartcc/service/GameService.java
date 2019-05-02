package com.codecool.dartcc.service;

import com.codecool.dartcc.exception.GameNotFoundException;
import com.codecool.dartcc.exception.NoCheckoutFoundException;
import com.codecool.dartcc.exception.PlayerUpdateException;
import com.codecool.dartcc.model.*;
import com.codecool.dartcc.repository.GameRepository;
import com.codecool.dartcc.repository.HintFor2Repository;
import com.codecool.dartcc.repository.HintFor3Repository;
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
    private HintFor3Repository hintFor3Repository;

    @Autowired
    private HintFor2Repository hintFor2Repository;

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

            p1.setGamesPlayed(p1.getGamesPlayed() + 1);
            p2.setGamesPlayed(p2.getGamesPlayed() + 1);
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
            if (updateData.getWinner() != null) {
                Player winner = playerService.findPlayerByName(updateData.getWinner());
                playerService.addWinToPlayer(winner);
                gameToUpdate.setWinner(winner);
                System.out.println(winner.getWins());
            }
            gameToUpdate.setRound(updateData.getRound());
            gameToUpdate.setNumberOfDoubles(updateData.getDoubles());
            gameToUpdate.setNumberOfTriples(updateData.getTriples());
            gameRepository.saveAndFlush(gameToUpdate);

        } catch (IOException | PlayerUpdateException e) {
            e.printStackTrace();
        }

    }

    public String getHintFor2Dart(int score) throws NoCheckoutFoundException {
        CheckoutFor2Dart checkout = hintFor2Repository.findCheckoutFor2DartsByScore(score);
        if (checkout == null) {
            throw new NoCheckoutFoundException("No possible 2 dart checkout for score: " + score);
        }
        return checkout.getCheckout();
    }

    public String getHintFor3Dart(int score) throws NoCheckoutFoundException {
        CheckoutFor3Dart checkout = hintFor3Repository.findCheckoutFor3DartsByScore(score);
        if (checkout == null) {
            throw new NoCheckoutFoundException("No possible 3 dart checkout for score: " + score);
        }
        return checkout.getCheckout();
    }
}
