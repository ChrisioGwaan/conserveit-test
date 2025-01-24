package com.conserveit.controlbg.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/v1/home")
    public String home() {
        return "Chrisio!";
    }

    @GetMapping("/")
    public String index() {
        return "Chrisio!";
    }

}
