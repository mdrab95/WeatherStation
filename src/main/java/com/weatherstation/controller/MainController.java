package com.weatherstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    public MainController() {
    }
    @GetMapping("/")
    public String mainController(){
        return "<html><title>Stacja pogodowa - info</title><body>"
                + "<h2>General information</h2>"
                + "<p>Encrypt your thingspeak password with com.weatherstation.util.PasswordEncrypter</p>"
                + "<p>Edit com.weatherstation.WeatherstationApplication - set your encrypted thingspeak password and list of sensors</p>"
                + "<p>build project with mvn clean install command</p>"
                + "</body></html>";
    }
}
