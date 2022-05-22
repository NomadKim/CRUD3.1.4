package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImplementation implements UserDaoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void delete(Long id) {
        User user2 = (User)entityManager.createQuery("select users from User users where users.id = '" +
                id +"'").getResultList().get(0);
        if(user2 != null){
            entityManager.remove(user2);
        }
    }

    @Override
    @Transactional
    public void update(User user) {
        User user2 = (User) entityManager.createQuery("select users from User users where users.id = '" +
                user.getId() + "'").getResultList().get(0);
        entityManager.detach(user2);
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setEmail(user.getEmail());
        user2.setAge(user.getAge());
        if (user.getPassword() != null && user.getPassword() != "") {
        user2.setPassword(user.getPassword());
        }
        user2.setRoles(user.getRoles());
        entityManager.merge(user2);
    }

    @Override
    @Transactional
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("select users from User users").
                getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        return (User)entityManager.createQuery("select users from User users where users.email = '" +
                username +"'").getResultList().get(0);
    }

    @Override
    public Set<Role> receiveRoles(int roles){
        Set<Role> returnRoles = new HashSet<>();
        List<Role> listOfRoles1 = entityManager.createQuery("select roles from Role roles").
                getResultList();
        ArrayList<Role> listOfRoles = new ArrayList<>(listOfRoles1);
        Role admin = null;
        Role user = null;

        for (Role role: listOfRoles){
            if(role.getRole().equals("ROLE_ADMIN")){
                admin = role;
            }else {
                user = role;
            }
        }
        if(roles == 1){
            returnRoles.add(admin);
        } else if(roles == 2){
            returnRoles.add(user);
        } else if(roles == 3){
            returnRoles.add(admin);
            returnRoles.add(user);
        }

        return returnRoles;

    }
}
