package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.UsersFriends;
import com.imdbapp.datamodels.databasemodel.UsersFriendsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserFriendsRepository extends JpaRepository<UsersFriends, UsersFriendsId> {
    List<UsersFriends> findByUsersFriendsId_RequesterId(Long id);

    List<UsersFriends> findByUsersFriendsId_AccepterId(Long id);
}
