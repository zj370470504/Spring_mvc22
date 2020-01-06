package com.bailiban.controller;

import com.bailiban.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("hello")
    public User hello(User user ) {
        return user;
    }

}
