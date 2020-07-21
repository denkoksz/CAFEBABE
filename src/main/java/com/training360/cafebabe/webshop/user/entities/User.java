package com.training360.cafebabe.webshop.user.entities;

public class User {

    private String name;

    private String password;

    private String realName;

    public User(String name, String password, String realName) {
        this.name = name;
        this.password = password;
        this.realName = realName;
    }

    public User(String name, String realName) {
        this.name = name;
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRealName() {
        return realName;
    }
}
