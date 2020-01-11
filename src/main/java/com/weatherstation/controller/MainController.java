package com.weatherstation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    public MainController() {
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainController(){
        return "<html><title>Weather Station - info</title><body>"
                + "<h2>General information</h2>"
                + "<p>https://github.com/mdrab95/WeatherStation</p>"
                + "</body></html>";
    }
}
