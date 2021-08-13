package com.qm.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "hello security";
    }

    @PostMapping("/user/login")
    public String login(String username,String password) {
        return username + password;
    }

    @GetMapping("/admin")
    public String test() {
        return "admin";
    }

}
