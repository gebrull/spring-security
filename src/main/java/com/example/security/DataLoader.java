package com.example.security;

import java.util.Arrays;

import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


// imports pro debug
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;




@Component
public class DataLoader implements CommandLineRunner{

    // debug
    // private static Logger logger = LoggerFactory.getLogger(DataLoader.class);
    




    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        
        roleRepository.save(new Role("USER"));
        
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");


        User user = new User("root@email.com", passwordEncoder.encode("root"),"Admin", "SUPER", true, "root");        
        user.setRoles(Arrays.asList(adminRole));        
        userRepository.save(user);

        user = new User("user@email.com", passwordEncoder.encode("senha"),"USUARIO", "PADRAO", true, "user");        
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
        
        

    }
    




}
