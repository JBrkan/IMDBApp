package com.imdbapp.Services;

import com.imdbapp.DataModels.ImdbApiCallSearchModel.Result;
import com.imdbapp.DataModels.ImdbApiCallSearchModel.SearchResults;
import com.imdbapp.DataModels.DatabaseModel.Movies;

import java.util.List;
import java.util.Set;


public interface SearchService {
    SearchResults Search(String searchQuery, String userName );
    Set<Movies> convertResultsToMovies(List<Result> resultList);
}
