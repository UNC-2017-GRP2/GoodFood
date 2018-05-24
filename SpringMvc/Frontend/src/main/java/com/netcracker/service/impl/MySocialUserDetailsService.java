package com.netcracker.service.impl;

import com.netcracker.model.User;
import com.netcracker.service.UserService;
import com.netcracker.user.MySocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service("socialUserDetailsService")
public class MySocialUserDetailsService implements SocialUserDetailsService {
    @Autowired
    private UserService userService;

    // Loads the UserDetails by using the userID of the user.
    // (This method Is used by Spring Security API).
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        User account= userService.getUserById(new BigInteger(userId));

        MySocialUserDetails userDetails= new MySocialUserDetails(account);

        return userDetails;
    }
}
