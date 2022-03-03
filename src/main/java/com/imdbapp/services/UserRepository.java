package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String username);


}