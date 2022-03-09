package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.Movies;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.imdbapicallsearchmodel.Result;
import com.imdbapp.datamodels.imdbapicallsearchmodel.SearchResults;
import com.imdbapp.exceptions.ApiCallFailedException;
import com.imdbapp.exceptions.UserDoesntExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final static String API_KEY = "k_qlcwkit9";
    private final static String URI = "https://imdb-api.com/en/API/SearchMovie/";
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;



    public SearchServiceImpl(RestTemplate restTemplate,
                             UserRepository userRepository){
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public SearchResults Search(String title, String userName) {

        String SearchURI = URI + API_KEY +"/"+ title;

        ResponseEntity<SearchResults> responseEntity = restTemplate.getForEntity(SearchURI, SearchResults.class);
        if(!responseEntity.hasBody()){
            throw new ApiCallFailedException("Api call failed");
        }
        SearchResults searchResults = responseEntity.getBody();


        Users users = userRepository.findByUserName(userName).orElseThrow(()-> new UserDoesntExistException("No User"));
        Set<Movies> moviesWatchedSet = users.getMovies();

            searchResults.getResults().forEach((movie) -> {
                for (Movies movieWatched : moviesWatchedSet) {
                    if (movie.id.equals(movieWatched.getImdbMovieId())) {
                        movie.setWatched(true);
                    }
                }
            });


        List<Result> resultList= searchResults.getResults().stream().sorted(Comparator.comparing(Result::isWatched)).collect(Collectors.toList());
        searchResults.setResults(resultList);
        return searchResults;
    }

    @Override
    public Set<Movies> convertResultsToMovies(List<Result> resultList){

        Set<Movies> moviesSet = new HashSet<>();
        for(Result result: resultList){
            moviesSet.add(new Movies(result.id,result.title,result.image));
        }

        return moviesSet;
    }


}


