package com.netcracker.user;

import com.netcracker.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySocialUserDetails implements SocialUserDetails{
    private static final long serialVersionUID = -5246117266247684905L;
    private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    private User myUserAccount;

    public MySocialUserDetails(User myUserAccount) {
        this.myUserAccount = myUserAccount;
        String role = myUserAccount.getRole();

        GrantedAuthority grant = new SimpleGrantedAuthority(role);
        this.list.add(grant);
    }
    @Override
    public String getUserId() {
        return this.myUserAccount.getUserId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return list;
    }

    @Override
    public String getPassword() {
        return myUserAccount.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return myUserAccount.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
