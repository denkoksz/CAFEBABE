package com.training360.cafebabe.webshop.user.service;

import com.training360.cafebabe.webshop.user.controller.UpdateUserRequest;
import com.training360.cafebabe.webshop.user.entities.User;
import com.training360.cafebabe.webshop.user.repository.CreateUserResponse;
import com.training360.cafebabe.webshop.user.repository.UpdateUserResponse;
import com.training360.cafebabe.webshop.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public CreateUserResponse createUser(String name, String password, String realName) {
                return repository.createUser(name, password, realName);
    }

    public UserNameAvailableResponse isUsernameAvailable(String username){
        boolean available = repository.isValidUsername(username);
        return new UserNameAvailableResponse(available);
    }
    public int getNumberOfUsers(){
        return repository.getNumberOfUsers();
    }
    public List<User> listAllUser() {
        return repository.listAllUser();
    }

    public UpdateUserResponse updateUser(String name, String password, String realName) {
        return repository.updateUser(name, password, realName);
    }

    public UpdateUserResponse deleteUser(String username) {
        return repository.deleteUser(username);
    }

    public User getUserByUserName(String userName) {
        return repository.getUserByUserName(userName);
    }

    public UpdateUserResponse updateUserRealName(String username, String realName) {
        return repository.updateUserRealName(username, realName);
    }

}
