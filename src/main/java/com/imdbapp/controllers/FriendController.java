package com.imdbapp.controllers;

import com.imdbapp.datamodels.AuthUserDetails;
import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.services.FriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/friends/search")
    public String searchForFriends(Principal loggedUser, Model model, @RequestParam("username") String username) {
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("UserWrapper", friendService.findFriends(username, loggedUser.getName()));
        return "friendSearch";
    }

    @GetMapping("/friends")
    public String getFriendList(Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        model.addAttribute("userInfo", userDetails.getUsername());
        model.addAttribute("friends", friendService.fetchFriends(userDetails.getUsername()));
        model.addAttribute("requests", friendService.fetchRequests(userDetails.getUsername()));
        return "friends";
    }

    @GetMapping("/friends/accept")
    public String acceptRequest(@RequestParam("userId") Long friendId, @AuthenticationPrincipal AuthUserDetails userDetails) {
        friendService.acceptRequest(friendId, userDetails.getUserId());
        return "redirect:/api/friends";
    }

    @PostMapping("friends/addFriend")
    public String addFriend(@ModelAttribute("UserWrapper") UserWrapper friends, @AuthenticationPrincipal AuthUserDetails userDetails) {
        friendService.addNewFriend(friends, userDetails.getUsername());
        return "redirect:/api/friends";
    }


}
