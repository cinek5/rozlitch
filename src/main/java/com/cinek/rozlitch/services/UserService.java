package com.cinek.rozlitch.services;

import com.cinek.rozlitch.exception.DuplicateUserException;
import com.cinek.rozlitch.models.User;
import com.cinek.rozlitch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cinek on 14.11.2017.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void registerUser(User user) {
         User result = userRepo.findByUsername(user.getUsername());
         if(result != null) {
             throw new DuplicateUserException();
         }
         save(user);

    }
    public User findByUserName(String name)  { return userRepo.findByUsername(name);}
    public List<User> findAll() {
        return userRepo.findAll();
    }

}
