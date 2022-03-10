package com.imdbapp.services;

import com.imdbapp.datamodels.databasemodel.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String username);

    Set<Users> findByUserNameIgnoreCaseContaining(String username);

    List<Users> findByUserIdIn(List<String> username);

    @Modifying
    @Query(value = "insert into users_friends VALUES(?1, ?2, ?3, ?4)", nativeQuery = true)
    void addFriendship(Long accepter, Long requester, Date befriended, String relationship);


}
