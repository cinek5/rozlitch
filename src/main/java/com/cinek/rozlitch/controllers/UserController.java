package com.cinek.rozlitch.controllers;

import com.cinek.rozlitch.exception.DuplicateUserException;
import com.cinek.rozlitch.helpers.SecurityHelper;
import com.cinek.rozlitch.models.User;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import com.cinek.rozlitch.repositories.UserRepository;
import com.cinek.rozlitch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.DefaultToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Cinek on 17.11.2017.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MoneyRequestRepository requestRepo;

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

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/logouts")
    public void logout(Principal principal) throws IOException {

        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
        consumerTokenServices.revokeToken(accessToken.getValue());

    }
}
