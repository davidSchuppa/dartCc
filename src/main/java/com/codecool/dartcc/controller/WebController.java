package com.codecool.dartcc.controller;

import com.codecool.dartcc.exception.GameNotFoundException;
import com.codecool.dartcc.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class WebController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/create-game", headers = "Accept=application/json")
    public ResponseEntity<?> createGame(@RequestBody String data) {
        //TODO
        //Increment players games played stat when game is created
        long gameId = gameService.createGame(data);
        return new ResponseEntity<>(gameId, HttpStatus.OK);
    }

    @PutMapping(value = "/turn", headers = "Accept=application/json")
    public ResponseEntity<?> turn(@RequestBody String gameData) {
        HttpStatus status = HttpStatus.OK;
        try {
            gameService.updateGame(gameData);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/hint-3/{score}")
    public String getHintFor3Dart(@PathVariable("score") int score) {
        String hint = gameService.getHintFor3Dart(score);
        return hint;
    }
}
