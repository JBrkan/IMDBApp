package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.Users;
import com.imdbapp.datamodels.databasemodel.UsersFriends;
import com.imdbapp.datamodels.databasemodel.UsersFriendsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserFriendsRepository extends JpaRepository<UsersFriends, UsersFriendsId> {
    List<UsersFriends> findByUsersFriendsId_RequesterName(String username);
    List<UsersFriends> findByUsersFriendsId_AccepterName(String username);
}
