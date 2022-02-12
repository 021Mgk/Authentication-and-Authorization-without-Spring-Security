package com.mgk021.model.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "AppUser")
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = AppUser.EMAIL_UNIQUE, query = "SELECT COUNT(o) FROM AppUser o WHERE o.email=:email", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = AppUser.IS_UNIQUE, query = "SELECT COUNT(o) FROM AppUser o WHERE o.username=:username", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = "userExist", query = "SELECT o FROM AppUser o WHERE o.username=:username AND o.password=:password", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = AppUser.ALL, query = "SELECT o FROM AppUser o", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = AppUser.COUNT_ALL, query = "SELECT COUNT(o) FROM AppUser o", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = AppUser.NAME_FILTER, query = "SELECT o FROM AppUser o WHERE  o.family LIKE :title OR o.name LIKE :title", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
        @NamedQuery(name = AppUser.COUNT_ALL_NAME_FILTER, query = "SELECT COUNT(o) FROM AppUser o WHERE  o.family LIKE :title OR o.name LIKE :title", hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppUser implements Serializable {

    public static final String EMAIL_UNIQUE = "AppUser.emailUnique";
    public static final String IS_UNIQUE = "AppUser.isUnique";
    public static final String ALL = "AppUser.all";
    public static final String COUNT_ALL = "AppUser.countAll";
    public static final String NAME_FILTER = "AppUser.nameFilter";
    public static final String COUNT_ALL_NAME_FILTER = "AppUser.countAllNameFilter";



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20, min = 3, message = "نام ضروری می باشد")
    @NotEmpty(message = "Please enter name")
    private String name;
    @NotBlank(message = "نام خانوادگی ضروری می باشد")
    private String family;
    @Column(unique = true)
    @Pattern(regexp = "\\b[a-z A-Z0-9._%+-]+@[a-z A-Z0-9.-]+\\.[a-z A-Z]{2,4}\\b", message = "پست الکترونیکی معتبر نمیباشد")
    private String email;
    @NotBlank(message = "username bad ast")
    private String username;
    @JsonIgnore
    @NotBlank(message = "کلمه عبور اجباری است")
    private String password;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_name")
    private Set<AppUserRole> appUserRoles = new HashSet(0);

    public AppUser() {
    }

    public AppUser(String name, String family, String email, String username, String password) {
        this.name = name;
        this.family = family;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AppUser(String name, String family, String email, String username, String password, Set<AppUserRole> appUserRoles) {
        this.name = name;
        this.family = family;
        this.email = email;
        this.username = username;
        this.password = password;
        this.appUserRoles = appUserRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AppUserRole> getAppUserRoles() {
        return appUserRoles;
    }

    public void setAppUserRoles(Set<AppUserRole> appUserRoles) {
        this.appUserRoles = appUserRoles;
    }

}