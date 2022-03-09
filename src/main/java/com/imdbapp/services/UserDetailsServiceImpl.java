package com.imdbapp.services;

import com.imdbapp.datamodels.AuthUserDetails;
import com.imdbapp.exceptions.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Qualifier("UserDetailsServiceImpl")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new AuthUserDetails(userRepository.findByUserName(username).orElseThrow(() ->new UserDoesntExistException("Your account has been removed")));

    }
}
