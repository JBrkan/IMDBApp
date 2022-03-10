package com.imdbapp.controllers;


import com.imdbapp.datamodels.databasemodel.UsersFriends;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.services.SearchService;
import com.imdbapp.services.UserFriendsRepository;
import com.imdbapp.services.UserRepository;
import com.imdbapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserFriendsRepository userFriendsRepository;


    public SearchController(SearchService searchService, UserRepository userRepository
            , UserService userService, UserFriendsRepository userFriendsRepository1) {
        this.searchService = searchService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userFriendsRepository = userFriendsRepository1;
    }


    @GetMapping("/home")
    public String home(Model model, Principal loggedUser) {
        List<UsersFriends> usersFriends = userFriendsRepository.findByUsersFriendsId_RequesterId(2L);
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("watchedMovies", userService.fetchWatchedMovies(userRepository.findByUserName(loggedUser.getName()).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"))));
        return "home";
    }

    @GetMapping("/search")
    public String searchEngine(Model model, Principal loggedUser) {

        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("movieList", new SearchResults());
        return "search";
    }

    @GetMapping("/search/results")
    public String searchMovie(Model model, Principal loggedUser, @RequestParam("title") String title) {
        if (title.isEmpty()) {
            model.addAttribute("userInfo", loggedUser.getName());
            model.addAttribute("movieList", new SearchResults());
            return "search";
        }
        model.addAttribute("userInfo", loggedUser.getName());
        model.addAttribute("movieList", searchService.Search(title, loggedUser.getName()));
        return "search";
    }


    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movieList") SearchResults searchResults, Model model, Principal loggedUser) {
        model.addAttribute("title", "");
        userService.addSelectedMovies(searchResults, loggedUser.getName());
        return "redirect:/api/search";
    }


}