package com.imdbapp.services;


import com.imdbapp.datamodels.databasemodel.Movies;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.exceptions.NoCheckBoxSelectionException;
import com.imdbapp.exceptions.UserDoesntExistException;
import com.imdbapp.exceptions.UserFormValidationException;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SearchService searchService;
    private final static Pattern CHECK_PATTERN = Pattern.compile("[^a-zA-Z0-9]");

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SearchService searchService){
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
        this.searchService = searchService;
    }

    @Override
    public void addNewUser(Users user) throws UserFormValidationException {
        List<String> errors = new ArrayList<>();
        Matcher match;
        boolean check;

        if(userRepository.findByUserName(user.getUserName()).isPresent()) {
            errors.add("Username already taken");
        }
        match= CHECK_PATTERN.matcher(user.getUserName());
        check = match.find();
        if(user.getUserName().length()< 5 || check){
            errors.add("Username should be at least 5 characters long and contain no special characters");
        }
        match = CHECK_PATTERN.matcher(user.getPassWord());
        check = match.find();
        if(user.getPassWord().length() < 5 || check){
            errors.add("Password should be at least 5 characters long and contain no special characters");
        }
        if(!errors.isEmpty()){
            throw new UserFormValidationException(errors);
        }
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setEnabled(true);
        user.setRoles("USER");
        userRepository.save(user);
    }

    @Override
    public Set<Movies> fetchWatchedMovies(Users users){
        return users.getMovies();
    }

    @Override
    public void addSelectedMovies(SearchResults searchResults){
        if(searchResults.getResults().isEmpty()){
            throw new NoCheckBoxSelectionException("");
        }
        searchResults.getResults().removeIf(result -> !result.getChecked());
        Set<Movies> moviesSet = new HashSet<>(searchService.convertResultsToMovies(searchResults.getResults()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findByUserName(authentication.getName()).orElseThrow(()-> new UserDoesntExistException("Your account has been removed"));
        users.addMovies(moviesSet);
        userRepository.save(users);
    }
}
