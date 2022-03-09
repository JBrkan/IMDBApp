package com.imdbapp.services;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.datamodels.databasemodel.Users;


public interface FriendService {
    UserWrapper findFriends(String username);
    void addNewFriend(UserWrapper userWrapper, String loggedUser);
}
