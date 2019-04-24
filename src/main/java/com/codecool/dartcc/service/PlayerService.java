package com.codecool.dartcc.service;

import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(String playerName) {
        Player player = playerRepository.findPlayerByName(playerName);
        if (player == null) {
            Player newPlayer = Player.builder().name(playerName).build();
            playerRepository.saveAndFlush(newPlayer);
            return newPlayer;
        }
        return player;
    }
}
