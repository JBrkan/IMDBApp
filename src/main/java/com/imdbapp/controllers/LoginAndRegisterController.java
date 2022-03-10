package com.imdbapp.controllers;

import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.exceptions.UserFormValidationException;
import com.imdbapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginAndRegisterController {

    private final UserService userService;

    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("newUser", new Users());
        return "register";
    }


    @PostMapping("/register/add")
    public String registerUser(Model model, @ModelAttribute("newUser") Users user) {

        try {
            userService.addNewUser(user);
            model.addAttribute("msg", "Successfully registered");
            return "login";
        } catch (UserFormValidationException ex) {
            model.addAttribute("newUser", new Users());
            model.addAttribute("msgList", ex.errors);
            return "register";
        }
    }


}
