package com.imdbapp.controllers;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.services.FriendService;
import com.imdbapp.services.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/api")
public class FriendController {

    private final UserRepository userRepository;
    private final FriendService friendService;

    public FriendController(UserRepository userRepository, FriendService friendService){
        this.userRepository = userRepository;
        this.friendService = friendService;
    }

    @GetMapping("/friends/search")
    public String searchForFriends(Principal loggedUser, Model model, @RequestParam("username")String username){
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("UserWrapper", friendService.findFriends(username) );
        return "friendSearch";
    }

    @GetMapping("/friends")
    public String getFriendList(Model model, Principal loggedUser){
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("friends", userRepository.findByUserName(loggedUser.getName()).orElseThrow(() -> new UserDoesntExistException("Your account has been removed")));
        return "friends";
    }
    @PostMapping("friends/addFriend")
    public String addFriend(Principal loggedUser, @ModelAttribute("UserWrapper") UserWrapper friends){
        friendService.addNewFriend(friends, loggedUser.getName());
        return "redirect:/api/friends";
    }
}
