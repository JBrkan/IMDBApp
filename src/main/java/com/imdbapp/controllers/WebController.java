package com.imdbapp.controllers;


import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.services.SearchService;
import com.imdbapp.services.UserRepository;
import com.imdbapp.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class WebController {

    private final SearchService searchService;
    private final UserRepository userRepository;
    private final UserService userService;



    public WebController(SearchService searchService, UserRepository userRepository
                         , UserService userService)
    {this.searchService = searchService;
    this.userRepository = userRepository;
    this.userService = userService;
    }



    @GetMapping("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userInfo", authentication.getName() );
        model.addAttribute("watchedMovies", userService.fetchWatchedMovies(userRepository.findByUserName(authentication.getName()).orElseThrow(() ->new UserDoesntExistException("Your account has been removed"))) );
        return "home";
    }

    @GetMapping("/search")
    public String searchEngine(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userInfo", authentication.getName() );
        model.addAttribute("movieList", new SearchResults());
        return "search";
    }

    @GetMapping("/search/results")
    public String searchMovie(Model model, @RequestParam("title")String title){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(title.isEmpty()){
            model.addAttribute("userInfo", authentication.getName());
            model.addAttribute("movieList", new SearchResults());
            return "search";
        }
        model.addAttribute("userInfo", authentication.getName());
        model.addAttribute("movieList", searchService.Search(title, authentication.getName()));
        return "search";
    }


    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movieList") SearchResults searchResults,Model model){
        model.addAttribute("title", "");
        userService.addSelectedMovies(searchResults);

        return "redirect:/api/search";
    }


}
