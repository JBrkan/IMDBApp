package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.Movies;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;

import java.util.Set;


public interface UserService {
    void addNewUser(Users users);
    Set<Movies> fetchWatchedMovies(Users users);
    void addSelectedMovies(SearchResults searchResults);

}
