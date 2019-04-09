package com.codecool.dartcc.controller;

import com.codecool.dartcc.model.Game;
import com.codecool.dartcc.model.Player;
import com.codecool.dartcc.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(value="/create-game", headers = "Accept=application/json")
    public String createGame(@RequestBody String data){
        return data;
    }

    @RequestMapping(value = "/turn", headers = "Accept=application/json")
    public String turn(){
        return "";
    }
}
