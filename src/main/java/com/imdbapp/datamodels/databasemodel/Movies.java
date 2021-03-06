package com.imdbapp.datamodels.databasemodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;
    private String imdbMovieId;
    private String movieName;
    private String movieImage;


    public Movies() {
    }

    public Movies(String imdbMovieId, String movieName, String movieImage) {

        this.imdbMovieId = imdbMovieId;
        this.movieName = movieName;
        this.movieImage = movieImage;
    }

    public String getImdbMovieId() {
        return imdbMovieId;
    }

    public void setImdbMovieId(String imdbMovieId) {
        this.imdbMovieId = imdbMovieId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
}
