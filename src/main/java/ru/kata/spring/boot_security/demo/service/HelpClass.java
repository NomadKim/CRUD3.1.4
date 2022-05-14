package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HelpClass {

    public static List<User> returnList(UserInterface userImplem){
        return userImplem.getListUsers().stream().sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }
}
