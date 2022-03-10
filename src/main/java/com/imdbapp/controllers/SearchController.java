package com.imdbapp.controllers;


import com.imdbapp.datamodels.AuthUserDetails;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.services.SearchService;
import com.imdbapp.services.UserRepository;
import com.imdbapp.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository;
    private final UserService userService;


    public SearchController(SearchService searchService, UserRepository userRepository
            , UserService userService) {
        this.searchService = searchService;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        model.addAttribute("userInfo", userDetails.getUsername());
        model.addAttribute("watchedMovies", userService.fetchWatchedMovies(userRepository.findByUserName(userDetails.getUsername()).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"))));
        return "home";
    }

    @GetMapping("/search")
    public String searchEngine(Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        model.addAttribute("userInfo", userDetails.getUsername());
        model.addAttribute("movieList", new SearchResults());
        return "search";
    }

    @GetMapping("/search/results")
    public String searchMovie(Model model, @RequestParam("title") String title,
                              @AuthenticationPrincipal AuthUserDetails userDetails) {
        if (title.isEmpty()) {
            model.addAttribute("userInfo", userDetails.getUsername());
            model.addAttribute("movieList", new SearchResults());
            return "search";
        }
        model.addAttribute("userInfo", userDetails.getUsername());
        model.addAttribute("movieList", searchService.Search(title, userDetails.getUsername()));
        return "search";
    }


    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movieList") SearchResults searchResults, Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        model.addAttribute("title", "");
        userService.addSelectedMovies(searchResults, userDetails.getUsername());
        return "redirect:/api/search";
    }


}
