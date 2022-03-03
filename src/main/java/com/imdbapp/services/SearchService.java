package com.imdbapp.services;

import com.imdbapp.datamodels.imdbapicallsearchmodel.Result;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.datamodels.databasemodel.Movies;

import java.util.List;
import java.util.Set;


public interface SearchService {
    SearchResults Search(String searchQuery, String userName );
    Set<Movies> convertResultsToMovies(List<Result> resultList);
}
