package com.imdbapp.Services;

import com.imdbapp.DataModels.DatabaseModel.Movies;
import com.imdbapp.DataModels.DatabaseModel.Users;
import com.imdbapp.DataModels.ImdbApiCallSearchModel.SearchResults;

import java.util.Set;


public interface UserService {
    void addNewUser(Users users);
    Set<Movies> fetchWatchedMovies(Users users);
    void addSelectedMovies(SearchResults searchResults);
}
