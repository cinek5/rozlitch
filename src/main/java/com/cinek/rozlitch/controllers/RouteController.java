package com.cinek.rozlitch.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by Cinek on 29.11.2017.
 */
@Controller
public class RouteController {
    @GetMapping("/")
    public String index() {
       return "index";
   }
    @GetMapping("/userPage")
    public String userPage() {return "userPage";}
    @GetMapping("/debts")
    public String debtsPage() { return "moneyRequestPage";}
    @GetMapping("/newRequest")
    public String requestsForm() {return "moneyRequestForm";}
    @GetMapping("/myRequests")
    public String requestedCreatedByUserPage() {
            return "myCreatedRequestPage";
    }


}
