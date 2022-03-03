package com.imdbapp.DataModels.DatabaseModel;


import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Users{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long userId;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String roles;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_movies",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private Set<Movies> movies = new HashSet<>();

    public Users(){}

    public Users(String userName, String passWord, boolean enabled, String roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Set<Movies> getMovies() {
        return movies;
    }

    public void addMovies(Set<Movies> movies){
        this.movies.addAll(movies);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
