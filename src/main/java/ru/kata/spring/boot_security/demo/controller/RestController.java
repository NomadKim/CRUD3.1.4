package ru.kata.spring.boot_security.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.HelpClass;
import ru.kata.spring.boot_security.demo.service.UserImplem;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class RestController {

    @Autowired
    private UserImplem userImplem;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/")
    public List<User> addUser(@RequestBody User user){
        //добавить проверку на роли
        userImplem.add(user);
        return receiveUsers();
    }
    @PutMapping("/")
    public List<User> updateUser(@RequestBody User user){
        // изменить пароль и ролли
        userImplem.update(user);
        return receiveUsers();
    }
    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable String id){
        try{
            userImplem.delete(Long.valueOf(id));
        }catch(Exception e){

        }
        return receiveUsers();
    }

    public List<User> receiveUsers(){
        List<User> usersList = new ArrayList<>();
        usersList.addAll(HelpClass.returnList(userImplem));
        return usersList;
    }
}
