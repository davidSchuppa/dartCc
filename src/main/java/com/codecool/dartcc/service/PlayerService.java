package com.codecool.dartcc.service;

import com.codecool.dartcc.exception.PlayerUpdateException;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.PlayerRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.apache.bcel.util.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ObjectMapper mapper;

    public Player savePlayer(String playerName) {
        Player player = playerRepository.findPlayerByName(playerName);
        if (player == null) {
            Player newPlayer = Player.builder().name(playerName).build();
            playerRepository.saveAndFlush(newPlayer);
            return newPlayer;
        }
        return player;
    }

    public void updatePlayersStats(String updateJson) throws PlayerUpdateException {
        try {
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            JsonNode p1Node = mapper.readTree(updateJson).get("playerOne");
            Player playerOneData = mapper.treeToValue(p1Node, Player.class);
            Player updatedP1 = updatePlayerData(playerOneData);

            JsonNode p2Node = mapper.readTree(updateJson).get("playerTwo");
            Player playerTwoData = mapper.treeToValue(p2Node, Player.class);
            Player updatedP2 = updatePlayerData(playerTwoData);
            playerRepository.save(updatedP1);
            playerRepository.save(updatedP2);

        } catch (IOException e) {
            e.printStackTrace();
            throw new PlayerUpdateException("Can't update players statistics");
        }
    }


    private Player updatePlayerData(Player data) {
        Player player = playerRepository.findPlayerByName(data.getName());
        player.setBestOfThree(data.getBestOfThree());
        player.setAvgPerDart(data.getAvgPerDart());
        player.setAvgPerRound(data.getAvgPerRound());
        return player;

    }

    public Player findPlayerByName(String name) {
        return playerRepository.findPlayerByName(name);
    }

    public void addWinToPlayer(Player player) {
        player.setWins(player.getWins() + 1);
        playerRepository.save(player);
    }
}
