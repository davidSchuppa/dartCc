package com.codecool.dartcc.controller;

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

    @PostMapping(value="/create-game", headers = "Accept=application/json")
    public ResponseEntity<?> createGame(@RequestBody String data){
        long gameId = gameService.createGame(data);
        return new ResponseEntity<>(gameId, HttpStatus.OK);
    }

    @RequestMapping(value = "/turn", headers = "Accept=application/json")
    public String turn(){
        return "";
    }
}
