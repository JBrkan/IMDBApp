package com.imdbapp.Services;


import com.imdbapp.DataModels.DatabaseModel.Movies;
import com.imdbapp.DataModels.DatabaseModel.Users;
import com.imdbapp.DataModels.ImdbApiCallSearchModel.SearchResults;
import com.imdbapp.Exceptions.UserDoesntExistException;
import com.imdbapp.Exceptions.UserFormValidationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    SearchService searchService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SearchService searchService){
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
        this.searchService = searchService;
    }

    @Override
    public void addNewUser(Users user) {
        List<String> errors = new ArrayList<>();
        Pattern checkPattern;
        Matcher match;
        boolean check;
        checkPattern = Pattern.compile("[^a-zA-Z0-9]");


        if(userRepository.findByUserName(user.getUserName()).isPresent()) {
            errors.add("Username already taken");
        }

        match= checkPattern.matcher(user.getUserName());
        check = match.find();
        if(user.getUserName().length()< 5 || check){
            errors.add("Username should be at least 5 characters long and contain no special characters");
        }
        match = checkPattern.matcher(user.getPassWord());
        check = match.find();
        if(user.getPassWord().length() < 5 || check){
            errors.add("Password should be at least 5 characters long and contain no special characters");
        }
        if(errors.isEmpty()){
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
            user.setEnabled(true);
            user.setRoles("USER");
            userRepository.save(user);
        }else{
            throw new UserFormValidationException(errors);
        }


    }

    @Override
    public Set<Movies> fetchWatchedMovies(Users users){

        return users.getMovies();
    }

    @Override
    public void addSelectedMovies(SearchResults searchResults){
        searchResults.getResults().removeIf(result -> !result.getChecked());

        Set<Movies> moviesSet = new HashSet<>(searchService.convertResultsToMovies(searchResults.getResults()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users users = userRepository.findByUserName(authentication.getName()).orElseThrow(()-> new UserDoesntExistException("Your account has been removed"));
        users.addMovies(moviesSet);
        userRepository.save(users);
    }
}
