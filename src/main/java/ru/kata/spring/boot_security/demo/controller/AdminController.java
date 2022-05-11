package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserImplem;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserImplem userImplem;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/admin")
    public String firstPage(Principal principal, ModelMap modelMap){
        receiveModelForPage(principal, modelMap);
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String secondPage(Principal principal, @PathVariable String id,
                             ModelMap modelMap){
        Long mainId = Long.valueOf(id);
        userImplem.delete(mainId);
        receiveModelForPage(principal, modelMap);
        return "admin";
    }

    @PostMapping (value = "/admin/add")
    public String thirdPage(Principal principal, HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = createUser(httpServletRequest);
        userImplem.add(user);
        receiveModelForPage(principal, modelMap);
        return "admin";
    }

    @PostMapping("/admin/update")
    public String fourthPage(Principal principal, HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = createUser(httpServletRequest);
        user.setId(Long.valueOf(httpServletRequest.getParameter("id")));
        if(user.getPassword() == ""){
            User userSecond = userImplem.getUserByUsername(user.getEmail());
            user.setPassword(userSecond.getPassword());
        }
        userImplem.update(user);
        receiveModelForPage(principal, modelMap);
        return "admin";
    }

    public List<User> returnList(){
        return userImplem.getListUsers().stream().sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    private User createUser(HttpServletRequest httpServletRequest){
        Set<Role> roles;
        String[] rolesString = httpServletRequest.getParameterValues("roles");
        if(rolesString.length == 2){
            roles = userImplem.receiveRoles(3);
        }else if(rolesString[0].equals("USER")){
            roles = userImplem.receiveRoles(2);
        }else if(rolesString[0].equals("ADMIN")){
            roles = userImplem.receiveRoles(1);
        } else {
            roles = userImplem.receiveRoles(2);
        }
        String password = "";
        if(httpServletRequest.getParameter("password") != null){
            passwordEncoder.encode(httpServletRequest.getParameter("password"));
        }
        User user = new User(httpServletRequest.getParameter("first_name"),
                httpServletRequest.getParameter("last_name"),
                Integer.valueOf(httpServletRequest.getParameter("age")),
                password,
                roles,
                httpServletRequest.getParameter("email"));
        return user;
    }

    public void receiveModelForPage(Principal principal, ModelMap modelMap){
        User user  = userImplem.getUserByUsername(principal.getName());
        Set<Role> sendRoles = new LinkedHashSet<>();
        if(user.getRoles().size() == 2){
            sendRoles.add(new Role("ROLE_ADMIN"));
            sendRoles.add(new Role("ROLE_USER"));
        } else {
            sendRoles.addAll(user.getRoles());
        }
        modelMap.addAttribute("usersList", returnList());
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", user.stringOfRoles());
        modelMap.addAttribute("sidemenuroles", sendRoles);
    }

//    @GetMapping("/")
//    public String returnIndex(){
//        User user = new User("ds",
//                "sd",
//                32,
//                passwordEncoder.encode("100"),
//                userImplem.receiveRoles(3),
//                "admin6");
//        user.setId(6l);
//        userImplem.update(user);
//        return "index";
//    }
}
