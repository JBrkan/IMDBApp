package com.imdbapp.datamodels.databasemodel;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "friend_id")}
    )
    private Set<Users> friends = new HashSet<>();
    @Transient
    boolean checked;

    public Users(){}

    public Users(String userName, String passWord, boolean enabled, String roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.enabled = enabled;
        this.roles = roles;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setMovies(Set<Movies> movies) {
        this.movies = movies;
    }

    public Set<Users> getFriends() {
        return friends;
    }

    public void setFriends(Set<Users> friends) {
        this.friends = friends;
    }

    public Set<Movies> getMovies() {
        return movies;
    }

    public void addMovies(Set<Movies> movies){
        this.movies.addAll(movies);
    }

    public void addFriends(List<Users> friends){
        this.friends.addAll(friends);
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
