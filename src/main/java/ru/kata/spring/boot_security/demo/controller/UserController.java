package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserImplem;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserImplem userImplem;

    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap modelMap){
        User user = userImplem.getUserByUsername(principal.getName());
        modelMap.addAttribute("informationOfUser", user);
        return "user";
    }
}
