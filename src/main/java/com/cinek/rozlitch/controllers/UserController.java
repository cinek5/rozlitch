package com.cinek.rozlitch.controllers;

import com.cinek.rozlitch.exception.DuplicateUserException;
import com.cinek.rozlitch.helpers.SecurityHelper;
import com.cinek.rozlitch.models.User;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import com.cinek.rozlitch.repositories.UserRepository;
import com.cinek.rozlitch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Cinek on 17.11.2017.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MoneyRequestRepository requestRepo;


    @PostMapping("/users")
    public void registerUser(@RequestBody @Valid User user) {
       if (userService.findAll().contains(user)){
           throw new DuplicateUserException();
       }
       userService.registerUser(user);
    }
    @GetMapping("/users")
        List<User> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/otherusers")
        List<User> getOtherUsers() {
              List<User>  users = userService.findAll();
              User user = userService.findByUserName(SecurityHelper.getLoggedInUsername());
              users.remove(user);
              return users;
       }
    @GetMapping("/getusername")
    public String getUsername() {
        return SecurityHelper.getLoggedInUsername();
    }

}
