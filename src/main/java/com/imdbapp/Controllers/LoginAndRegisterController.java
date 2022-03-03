package com.imdbapp.Controllers;

import com.imdbapp.DataModels.DatabaseModel.Users;
import com.imdbapp.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginAndRegisterController {

    UserService userService;

    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("newUser", new Users());
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        return "redirect:/login";
    }



    @PostMapping("/register/add")
    public String registerUser(Model model,@ModelAttribute("newUser") Users user){

        userService.addNewUser(user);
        model.addAttribute("msg", "Successfully registered");
        return "login";
    }


}
