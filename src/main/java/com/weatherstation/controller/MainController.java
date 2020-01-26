package com.weatherstation.controller;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    String encryptedPassword;

    public MainController() {
        encryptedPassword = "";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView password() {
        return new ModelAndView("password");
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ModelAndView encryptPassword(@RequestParam String password, @RequestParam String appPassword, Model model) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(appPassword.toCharArray());
        encryptedPassword = textEncryptor.encrypt(password);
        model.addAttribute("password", encryptedPassword);
        return new ModelAndView("encryptedPassword");
    }

    @RequestMapping(value = "/encryptedpassword", method = RequestMethod.GET)
    public ModelAndView showEncryptedPassword(Model model) {
        model.addAttribute("password", encryptedPassword);
        return new ModelAndView("encryptedpassword");
    }
}
