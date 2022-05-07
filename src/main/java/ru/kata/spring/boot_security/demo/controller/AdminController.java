package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserImplem;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserImplem userImplem;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
    public String firstPage(Principal principal, ModelMap modelMap){
        modelMap.addAttribute("usersList", returnList());
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", userImplem.getUserByUsername(principal
                .getName()).stringOfRoles());
        return "admin";
    }

    @GetMapping("/admin/delete")
    public String secondPage(Principal principal, HttpServletRequest httpServletRequest, ModelMap modelMap){
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        System.out.println(id);
        try{
            userImplem.delete(id);
        }catch (Exception e){

        }
        modelMap.addAttribute("usersList", returnList());
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", userImplem.getUserByUsername(principal
                .getName()).stringOfRoles());        return "admin";
    }

    @PostMapping (value = "/admin/add")
    public String thirdPage(Principal principal, HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = createUser(httpServletRequest);
        userImplem.add(user);
        modelMap.addAttribute("usersList", returnList());
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", userImplem.getUserByUsername(principal
                .getName()).stringOfRoles());        return "admin";
    }

    @PostMapping("/admin/update")
    public String fourthPage(Principal principal, HttpServletRequest httpServletRequest, ModelMap modelMap){
        User user = createUser(httpServletRequest);
        user.setId(Long.valueOf(httpServletRequest.getParameter("id")));
        try{
            userImplem.update(user);
        }catch (Exception e){

        }
        modelMap.addAttribute("usersList", returnList());
        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roles", userImplem.getUserByUsername(principal
                .getName()).stringOfRoles());
        return "admin";
    }

    public List<User> returnList(){
        return userImplem.getListUsers().stream().sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    private User createUser(HttpServletRequest httpServletRequest){
        Set<Role> roles = null;
        if(httpServletRequest.getParameter("user_check") != null &&
                httpServletRequest.getParameter("admin_check") != null){
            roles = userImplem.receiveRoles(3);
        }else if(httpServletRequest.getParameter("user_check") != null){
            roles = userImplem.receiveRoles(2);
        }else if(httpServletRequest.getParameter("admin_check") != null){
            roles = userImplem.receiveRoles(1);
        } else {
            roles = userImplem.receiveRoles(2);
        }

        String password = passwordEncoder.encode(httpServletRequest.getParameter("password"));
        User user = new User(httpServletRequest.getParameter("first_name"),
                httpServletRequest.getParameter("last_name"),
                Integer.valueOf(httpServletRequest.getParameter("age")),
                password,
                roles,
                httpServletRequest.getParameter("email"));
        List<User> users = returnList();
        user.setId(users.get(users.size()-1).getId() + 1);
        return user;
    }

//    @GetMapping("/")
//    public String mainPage(){
//        User user = new User("admin",
//                "da",
//                32,
//                "$2a$12$H5J9Tbf5NoBfdeigxP0DHe8Hi/.KP53G/NSpo0ECZqhZnYjOdEbVO",
//                userImplem.receiveRoles(3),
//                "admin6");
//        user.setId(6l);
//        userImplem.add(user);
//        return "index";
//
//    }
}
