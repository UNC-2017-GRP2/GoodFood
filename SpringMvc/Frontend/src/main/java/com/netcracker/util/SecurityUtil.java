package com.netcracker.util;

import com.netcracker.model.User;
import com.netcracker.user.MySocialUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    // Auto login.
    public static void logInUser(User user) {

        MySocialUserDetails userDetails = new MySocialUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
