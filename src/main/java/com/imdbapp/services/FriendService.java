package com.imdbapp.services;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.datamodels.databasemodel.Users;

import java.util.List;


public interface FriendService {
    UserWrapper findFriends(String username);
    void addNewFriend(UserWrapper userWrapper, String loggedUser);
    List<Users> fetchFriends(String username);
}
