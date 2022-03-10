package com.imdbapp.services;

import com.imdbapp.datamodels.UserWrapper;
import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.databasemodel.UsersFriends;
import com.imdbapp.exceptions.NoCheckBoxSelectionException;
import com.imdbapp.exceptions.UserDoesntExistException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FriendServiceImpl implements FriendService {

    private final UserFriendsRepository userFriendsRepository;
    private final UserRepository userRepository;

    public FriendServiceImpl(UserFriendsRepository userFriendsRepository, UserRepository userRepository) {
        this.userFriendsRepository = userFriendsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserWrapper findFriends(String username, String loggedUser) {
        List<Users> users = new ArrayList<>(userRepository.findByUserNameIgnoreCaseContaining(username));
        users.removeIf(user -> user.getUserName().equals(loggedUser));
        return new UserWrapper(users);
    }

    @Override
    public void addNewFriend(UserWrapper friends, String loggedUser) {
        Users loggedIn = userRepository.findByUserName(loggedUser).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"));
        if (friends.getUsers() == null) {
            throw new NoCheckBoxSelectionException("");
        }
        friends.getUsers().removeIf(user -> !user.getChecked());
        for (Users friend : friends.getUsers()) {
            userRepository.addFriendship(friend.getUserId(), loggedIn.getUserId(), Calendar.getInstance().getTime(), "PENDING");
        }

    }

    @Override
    public List<Users> fetchFriends(String username) {
        Users user = userRepository.findByUserName(username).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"));
        List<UsersFriends> usersFriends = userFriendsRepository.findByUsersFriendsId_RequesterId(user.getUserId());
        usersFriends.removeIf(friend -> !friend.getRelationship().equals("FRIENDS"));
        List<Users> friends = usersFriends.stream().map(UsersFriends::getAccepter).collect(Collectors.toList());
        usersFriends.clear();
        usersFriends = userFriendsRepository.findByUsersFriendsId_AccepterId(user.getUserId());
        usersFriends.removeIf(friend -> !friend.getRelationship().equals("FRIENDS"));
        friends.addAll(usersFriends.stream().map(UsersFriends::getRequester).collect(Collectors.toList()));

        return friends;
    }

    @Override
    public List<Users> fetchRequests(String username) {
        Users user = userRepository.findByUserName(username).orElseThrow(() -> new UserDoesntExistException("Your account has been removed"));
        List<UsersFriends> usersFriends = userFriendsRepository.findByUsersFriendsId_AccepterId(user.getUserId());
        usersFriends.removeIf(friend -> friend.getRelationship().equals("FRIENDS"));
        return usersFriends.stream().map(UsersFriends::getRequester).collect(Collectors.toList());
    }

    @Override
    public void acceptRequest(Long friendId, Long loggedUserId) {
        List<UsersFriends> requests = userFriendsRepository.findByUsersFriendsId_AccepterId(loggedUserId);
        requests.forEach((request) -> {
            if (request.getUsersFriendsId().getRequesterId() == friendId) {
                request.setRelationship("FRIENDS");
            }
        });
        userFriendsRepository.saveAll(requests);
    }
}
