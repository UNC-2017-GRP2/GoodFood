package com.netcracker.service.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.User;
import com.netcracker.repository.Repository;
import com.netcracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Repository repository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Entity user = repository.getEntityByName(username, Constant.USER_OBJ_TYPE_ID);
        UserDetails userDetails = null;
        if (user != null){
            GrantedAuthority authority = new SimpleGrantedAuthority(user.getParameterById(Constant.USER_ROLE_ATTR_ID).toString());
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getParameterById(Constant.USERNAME_ATTR_ID).toString(),
                    user.getParameterById(Constant.PASSWORD_HASH_ATTR_ID).toString(),
                    Arrays.asList(authority));
        }

        /*User user = userRepository.getUserByUsername(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPasswordHash(), Arrays.asList(authority));*/
        return userDetails;
    }
}
