package com.imdbapp.services;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.databasemodel.UsersFriends;
import com.imdbapp.exceptions.NoCheckBoxSelectionException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FriendServiceImpl implements FriendService{

    private final UserFriendsRepository userFriendsRepository;
    private final UserRepository userRepository;

    public FriendServiceImpl(UserFriendsRepository userFriendsRepository, UserRepository userRepository) {
        this.userFriendsRepository = userFriendsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserWrapper findFriends(String username) {
        List<Users> users = new ArrayList<>(userRepository.findByUserNameIgnoreCaseContaining(username));
        return new UserWrapper(users);
    }

    @Override
    public void addNewFriend(UserWrapper friends, String loggedUser) {
        if(friends.getUsers().isEmpty()){
            throw new NoCheckBoxSelectionException("");
        }
        friends.getUsers().removeIf(user -> !user.getChecked());
        for(Users friend: friends.getUsers()){
            userRepository.addFriendship(friend.getUserName(),loggedUser, Calendar.getInstance().getTime(),"PENDING");
        }

    }
}
