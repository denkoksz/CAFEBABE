package com.training360.cafebabe.webshop.order.entities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FakeAuthentication implements Authentication {
    private String name;
    private List<GrantedAuthority> roles;

    public FakeAuthentication(String name, String role) {
        this.name = name;
        this.roles = new ArrayList<>();
        this.roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return new User(name, "password", roles);
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return name;
    }
}
