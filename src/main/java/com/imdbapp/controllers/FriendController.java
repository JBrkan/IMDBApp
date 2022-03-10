package com.imdbapp.controllers;

import com.imdbapp.datamodels.AuthUserDetails;
import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.services.FriendService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String getFriendList(Model model, Principal loggedUser) {
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("friends", friendService.fetchFriends(loggedUser.getName()));
        model.addAttribute("requests", friendService.fetchRequests(loggedUser.getName()));
        return "friends";
    }

    @GetMapping("/friends/accept")
    public String acceptRequest(@RequestParam("userId") Long friendId, @AuthenticationPrincipal AuthUserDetails userDetails){
        friendService.acceptRequest(friendId, userDetails.getUserId());
        return "redirect:/api/friends";
    }

    @PostMapping("friends/addFriend")
    public String addFriend(Principal loggedUser, @ModelAttribute("UserWrapper") UserWrapper friends) {
        friendService.addNewFriend(friends, loggedUser.getName());
        return "redirect:/api/friends";
    }


}
