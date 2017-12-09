package com.netcracker.edu.service;

import com.netcracker.edu.dao.UserDao;
import com.netcracker.edu.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserInfo user = userDao.getUserByUsername(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = (UserDetails)new User(user.getUsername(),
                user.getPassHash(), Arrays.asList(authority));
        return userDetails;
    }
}