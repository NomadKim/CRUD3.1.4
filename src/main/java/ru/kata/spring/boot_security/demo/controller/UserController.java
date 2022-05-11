package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserImplem;
import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserImplem userImplem;

    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap modelMap){
        User user = userImplem.getUserByUsername(principal.getName());
        Set<Role> sendRoles = new LinkedHashSet<>();
        if(user.getRoles().size() == 2){
            sendRoles.add(new Role("ROLE_ADMIN"));
            sendRoles.add(new Role("ROLE_USER"));
        } else {
            sendRoles.addAll(user.getRoles());
        }
        modelMap.addAttribute("informationOfUser", user);
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", user.stringOfRoles());
        modelMap.addAttribute("sidemenuroles", sendRoles);
        modelMap.addAttribute("tempUser", user);
        return "user";
    }
}
