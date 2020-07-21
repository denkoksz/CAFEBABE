package com.training360.cafebabe.webshop.user.repository;

import com.training360.cafebabe.webshop.user.controller.CreateUserRequest;
import com.training360.cafebabe.webshop.user.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/addUsers.sql")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void createUser() {
        assertThat(repository.listAllUser().size(), equalTo(0));
        repository.createUser("valaki", "valami", "anonymus");
        assertThat(repository.listAllUser().size(), equalTo(1));
        repository.createUser("valaki", "aaaa", "anonyaaamus");
        assertThat(repository.listAllUser().size(), equalTo(1));
    }

    @Test
    public void createUserWithEmptyString() {
        assertThat(repository.listAllUser().size(), equalTo(0));
        repository.createUser("    ", "aaaa", "anonymus");
        assertThat(repository.listAllUser().size(), equalTo(0));
    }

    @Test
    public void updateUserTest() {
        repository.createUser("john_doe", "password", "John Doe");
        assertThat(repository.listAllUser().get(0).getRealName(), equalTo("John Doe"));
        repository.updateUser("john_doe", "password", "modified");
        assertThat(repository.listAllUser().get(0).getRealName(), equalTo("modified"));
    }


    @Test
    public void deleteUser() {
        repository.createUser("john_doe", "pw", "John Doe");
        assertThat(repository.listAllUser().size(), equalTo(1));
        repository.deleteUser("john_doe");
        assertThat(repository.listAllUser().size(), equalTo(0));
    }
}
