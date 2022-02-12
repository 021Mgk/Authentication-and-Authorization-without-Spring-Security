package com.mgk021.model.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "AppUserRole")
@Table(name = "users_roles")
public class AppUserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "role_name")
    private String rolename;

    public AppUserRole() {
    }

    public AppUserRole(String username, String rolename) {
        this.username = username;
        this.rolename = rolename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
