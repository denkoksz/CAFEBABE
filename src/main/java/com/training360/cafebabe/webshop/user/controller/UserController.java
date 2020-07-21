package com.training360.cafebabe.webshop.user.controller;


import com.training360.cafebabe.webshop.user.entities.User;
import com.training360.cafebabe.webshop.user.repository.CreateUserResponse;
import com.training360.cafebabe.webshop.user.repository.UpdateUserResponse;
import com.training360.cafebabe.webshop.user.service.UserNameAvailableResponse;
import com.training360.cafebabe.webshop.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //aktuálisan bejelentkezett felhasználót adja vissza
    @RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
    public CurrentUserResponse getCurrentUser(Authentication authentication) {
        CurrentUserResponse response = new CurrentUserResponse();
        if (authentication != null) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
            return response.setPresent(true)
                    .setUsername(details.getUsername())
                    .setAuthorities((Collection<SimpleGrantedAuthority>) details.getAuthorities());


        } else {
            return response.setPresent(false).setUsername("").setAuthorities(Collections.emptyList());
        }

    }

    //új felhasználó készítése, jelszót hash-ként titkosítja
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return service.createUser(request.getName(), new BCryptPasswordEncoder(4).encode(request.getPassword()), request.getRealName());
    }
    @RequestMapping(value="/api/users", params = "usernamecheck")
    public UserNameAvailableResponse isUserNameAvailable(@RequestParam("usernamecheck") String username){
        return service.isUsernameAvailable(username);

    }

    @RequestMapping("/api/users")
    public List<User> listAllUser(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.listAllUser();
        }
        return Collections.emptyList();

    }

    // felhasználó modosítása. ha üres jelszót kap, nem módosítja az eredeti jelszót
    @RequestMapping(value = "/api/users/{username}" ,method = RequestMethod.POST)
    public UpdateUserResponse updateUser(@PathVariable String username, @RequestBody UpdateUserRequest request, Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            if (request.getPassword().trim().length() != 0) {
                return service.updateUser(username, new BCryptPasswordEncoder(4).encode(request.getPassword()), request.getRealName());
            }
            return service.updateUserRealName(username, request.getRealName());
        }
        return new UpdateUserResponse(false, "You do not have previligies to update user");
    }


    @RequestMapping(value = "/api/users", params = "username", method = RequestMethod.DELETE)
    public UpdateUserResponse deleteUser(@RequestParam("username") String userName, Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.deleteUser(userName);
        }
        return new UpdateUserResponse(false, "You do not have previligies to delete user");
    }

    @RequestMapping(value = "/api/users", params = "username")
    public User getUserByUserName(@RequestParam("username") String userName) {
        return service.getUserByUserName(userName);
    }

    private boolean isAdmin(Authentication authentication) {
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

}
