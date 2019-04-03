package com.codecool.dartcc.controll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
