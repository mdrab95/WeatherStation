package com.weatherstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    public MainController() {
    }
    @GetMapping("/")
    public String mainController(){
        return "index";
    }
}
