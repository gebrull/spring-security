package com.example.security.controllers;

import org.springframework.stereotype.Controller;



import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    // Esse método tava estava sendo usado com o formLogin "padrão do spring"
    // @RequestMapping("/")
    // public String login(){
    //     return "login";
    // }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }
}
