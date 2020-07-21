package com.training360.cafebabe.webshop.user.controller;

import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CurrentUserResponse {
    private boolean present;
    private String username;
    private Collection<SimpleGrantedAuthority> authorities;

    public CurrentUserResponse setPresent(boolean present) {
        this.present = present;
        return this;
    }

    public CurrentUserResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public CurrentUserResponse setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public boolean isPresent() {
        return present;
    }

    public String getUsername() {
        return username;
    }

    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
    //TODO : user role, visszaküldeni ezt az egészet a böngészőnek
}
