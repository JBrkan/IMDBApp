package com.imdbapp.controllers;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.services.FriendService;
import com.imdbapp.services.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



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
    public String searchForFriends(Model model, @RequestParam("username")String username){
        model.addAttribute("UserWrapper", friendService.findFriends(username) );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userInfo", authentication.getName());
        return "friendSearch";
    }

    @GetMapping("/friends")
    public String getFriendList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("friends", userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new UserDoesntExistException("Your account has been removed")));
        return "friends";
    }
    @PostMapping("friends/addFriend")
    public String addFriend(@ModelAttribute("UserWrapper") UserWrapper friends){

        friendService.addNewFriend(friends);
        return "redirect:/friends/search";


    }
}
