package com.imdbapp.Controllers;


import com.imdbapp.DataModels.ImdbApiCallSearchModel.SearchResults;
import com.imdbapp.Exceptions.UserDoesntExistException;
import com.imdbapp.Services.SearchService;
import com.imdbapp.Services.UserRepository;
import com.imdbapp.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class WebController {

    public SearchService searchService;
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;
    public UserService userService;



    public WebController(SearchService searchService, UserRepository userRepository,
                         PasswordEncoder passwordEncoder, UserService userService)
    {this.searchService = searchService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
        model.addAttribute("userInfo", authentication.getName() );
        model.addAttribute("movieList", searchService.Search(title, authentication.getName()));
        return "search";
    }


    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movieList") SearchResults searchResults ){

        userService.addSelectedMovies(searchResults);

        return "redirect:/api/home";
    }


}
