package com.example.security.controllers;

import javax.validation.Valid;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    
    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/admin/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/admin/process")
    public String processForm(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "userForm";
        }
        userRepository.save(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/list")
    public String listUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "listUsers";
    }
}
