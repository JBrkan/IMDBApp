package com.imdbapp.Services;

import com.imdbapp.DataModels.DatabaseModel.Movies;
import com.imdbapp.DataModels.DatabaseModel.Users;
import com.imdbapp.DataModels.ImdbApiCallSearchModel.Result;
import com.imdbapp.DataModels.ImdbApiCallSearchModel.SearchResults;
import com.imdbapp.Exceptions.ApiCallFailedException;
import com.imdbapp.Exceptions.UserDoesntExistException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    final static String apiKey = "k_qlcwkit9";
    final static String URI = "https://imdb-api.com/en/API/SearchMovie/";
    public RestTemplate restTemplate;
    public HttpEntity<HttpHeaders> httpEntity;
    public UserRepository userRepository;



    public SearchServiceImpl(RestTemplate restTemplate,HttpEntity<HttpHeaders> httpEntity,
                             UserRepository userRepository){
        this.restTemplate = restTemplate;
        this.httpEntity = httpEntity;
        this.userRepository = userRepository;
    }

    @Override
    public SearchResults Search(String title, String userName) {

        String SearchURI = URI + apiKey +"/"+ title;

        ResponseEntity<SearchResults> responseEntity = restTemplate.exchange(SearchURI, HttpMethod.GET, httpEntity, SearchResults.class);

        SearchResults searchResults = responseEntity.getBody();


        Users users = userRepository.findByUserName(userName).orElseThrow(()-> new UserDoesntExistException("No User"));
        Set<Movies> moviesWatchedSet = users.getMovies();

        if(searchResults == null){
            throw new ApiCallFailedException("Api call returned 0 results");
        }
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


