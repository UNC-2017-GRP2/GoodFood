package com.netcracker.service.impl;

import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import com.netcracker.user.MySocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.math.BigInteger;

public class MySocialUserDetailsService implements SocialUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // Loads the UserDetails by using the userID of the user.
    // (This method Is used by Spring Security API).
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        User account= userRepository.getUserById(new BigInteger(userId));

        MySocialUserDetails userDetails= new MySocialUserDetails(account);

        return userDetails;
    }
}
