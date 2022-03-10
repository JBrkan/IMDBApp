package com.imdbapp.datamodels.databasemodel;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String roles;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_movies",
            joinColumns = {@JoinColumn(name = "user_name")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    private Set<Movies> movies = new HashSet<>();
    @OneToMany(mappedBy = "requester")
    private List<UsersFriends> requested;
    @OneToMany(mappedBy = "accepter")
    private List<UsersFriends> accepted;

    @Transient
    private Boolean checked = false;

    public Users() {
    }

    public Users(String userName, String passWord, boolean enabled, String roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.enabled = enabled;
        this.roles = roles;
    }

    public List<Users> getFriends() {
        return this.requested.stream().map(UsersFriends::getRequester).collect(Collectors.toList());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<UsersFriends> getRequested() {
        return requested;
    }

    public void setRequested(List<UsersFriends> requested) {
        this.requested = requested;
    }

    public List<UsersFriends> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<UsersFriends> accepted) {
        this.accepted = accepted;
    }

    public Set<Movies> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movies> movies) {
        this.movies = movies;
    }

    public void addMovies(Set<Movies> movies) {
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
