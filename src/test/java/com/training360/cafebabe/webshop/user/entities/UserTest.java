package com.training360.cafebabe.webshop.user.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void createUser() {
        User user = new User("john_doe", "johndoe", "John Doe");

        assertThat(user.getName(), equalTo("john_doe"));
        assertThat(user.getRealName(), equalTo("John Doe"));
    }
}
