package com.netcracker.service.impl;

import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import com.netcracker.user.MySocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    public MyUserDetailsService() {

    }

    // (This method is used by Spring Security API).
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

       User myUserAccount = userRepository.getUserByUsername(userName);

        if (myUserAccount == null) {
            throw new UsernameNotFoundException("No user found with userName: " + userName);
        }
        // Note: SocialUserDetails extends UserDetails.
        SocialUserDetails principal = new MySocialUserDetails(myUserAccount);

        return principal;
    }

}
