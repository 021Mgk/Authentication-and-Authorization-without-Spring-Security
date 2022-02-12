package com.mgk021.model.entity;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "roles")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "role_name")
    private Set<AppUserRole> appUserRoles = new HashSet(0);

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, Set<AppUserRole> appUserRoles) {
        this.roleName = roleName;
        this.appUserRoles = appUserRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<AppUserRole> getAppUserRoles() {
        return appUserRoles;
    }

    public void setAppUserRoles(Set<AppUserRole> appUserRoles) {
        this.appUserRoles = appUserRoles;
    }
}
