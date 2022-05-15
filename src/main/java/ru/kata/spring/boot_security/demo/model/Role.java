package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "authority")
    private String role;

    public Role(){

    }

    public Role(String role){
        this.role = role;
    }


    @Override
    public String toString() {
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

}
