package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import ru.kata.spring.boot_security.demo.service.UserInterface;

import javax.sql.DataSource;
import java.util.List;

public class JDBCUserDetailsManagerSecond extends JdbcUserDetailsManager {

        @Autowired
        private UserInterface userDAO;

    public JDBCUserDetailsManagerSecond(DataSource dataSource) {
            super(dataSource);
        }


        @Override
        protected List<GrantedAuthority> loadUserAuthorities(String username) {
            String userId = userDAO.getUserByUsername(username).getId().toString();
            return super.loadUserAuthorities(userId);
        }
    }
