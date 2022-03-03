package com.imdbapp.services;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.exceptions.UserDoesntExistException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService{

    private final UserRepository userRepository;

    public FriendServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserWrapper findFriends(String username) {
        List<Users> users = new ArrayList<>();
        users.add(userRepository.findByUserName(username).orElseThrow(()->new UserDoesntExistException("User no")));
        return new UserWrapper(users);
    }

    @Override
    public void addNewFriend(UserWrapper friends) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"));
        user.addFriends(friends.getUsers());
        userRepository.save(user);
    }
}
