package com.yaouguoji.platform.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class HearthController {

    @GetMapping("/index")
    public String index() {
        return "hello, I'm alive.";
    }
}
