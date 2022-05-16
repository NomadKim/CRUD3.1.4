package ru.kata.spring.boot_security.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.HelpClass;
import ru.kata.spring.boot_security.demo.service.UserImplem;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin/users")
public class RestController {

    @Autowired
    private UserImplem userImplem;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public List<User> getUsers(){
        return HelpClass.returnList(userImplem);
    }
    @PostMapping("/")
    public User addUser(@RequestBody User user){
        List<Role> usersList = new ArrayList<>();
        if (user.getRoles().size() != 0){
            usersList.addAll(user.getRoles());
        }
        if(user.getRoles().size() == 0){
            user.setRoles(userImplem.receiveRoles(2));
        } else if (user.getRoles().size() == 2){
            user.setRoles(userImplem.receiveRoles(3));
        } else if(usersList.get(0).getRole().equals("USER")){
            user.setRoles(userImplem.receiveRoles(2));
        } else if(usersList.get(0).getRole().equals("ADMIN")){
            user.setRoles(userImplem.receiveRoles(1));
        } else{
            user.setRoles(userImplem.receiveRoles(2));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userImplem.add(user);
        User userTosend = userImplem.getUserByUsername(user.getEmail());
        return userTosend;
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user){
        List<Role> usersList = new ArrayList<>();
        if (user.getRoles().size() != 0){
            usersList.addAll(user.getRoles());
        }
        if(user.getRoles().size() == 0){
            user.setRoles(userImplem.receiveRoles(2));
        } else if (user.getRoles().size() == 2){
            user.setRoles(userImplem.receiveRoles(3));
        } else if(usersList.get(0).getRole().equals("USER")){
            user.setRoles(userImplem.receiveRoles(2));
        } else if(usersList.get(0).getRole().equals("ADMIN")){
            user.setRoles(userImplem.receiveRoles(1));
        } else{
            user.setRoles(userImplem.receiveRoles(2));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userImplem.update(user);
        User userTosend = userImplem.getUserByUsername(user.getEmail());
        return userTosend;

    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userImplem.delete(Long.valueOf(id));
    }

    public User createUser(User user){
        return null;

    }
}
