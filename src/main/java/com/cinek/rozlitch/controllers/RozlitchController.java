package com.cinek.rozlitch.controllers;

import com.cinek.rozlitch.helpers.SecurityHelper;
import com.cinek.rozlitch.models.MoneyRequest;
import com.cinek.rozlitch.models.User;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import com.cinek.rozlitch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by Cinek on 09.11.2017.
 */
@RestController
public class RozlitchController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    MoneyRequestRepository requestRepo;


}
