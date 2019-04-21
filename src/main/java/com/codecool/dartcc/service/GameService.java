package com.codecool.dartcc.service;

import com.codecool.dartcc.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class GameService {

    @Autowired
    GameRepository gameRepository;


    public long createGame(String playerNames) {
        //TODO
        //Add gameData parameter, create new game object and save to db
        return 0;
    }
}
