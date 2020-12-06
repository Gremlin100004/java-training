package com.senla.socialnetwork.service.security;

import com.senla.socialnetwork.domain.SystemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    public UserPrincipal(SystemUser systemUser) {
        this.login = systemUser.getEmail();
        this.password = systemUser.getPassword();
        this.grantedAuthorities = Collections.singletonList(
            new SimpleGrantedAuthority(systemUser.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
