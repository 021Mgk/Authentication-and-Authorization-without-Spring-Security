package com.mgk021;

import com.mgk021.model.entity.AppUser;
import com.mgk021.model.entity.AppUserRole;
import com.mgk021.model.entity.Role;
import com.mgk021.model.entity.RoleAccess;
import com.mgk021.model.service.AppUserRoleService;
import com.mgk021.model.service.AppUserService;
import com.mgk021.model.service.RoleAccessService;
import com.mgk021.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationAuthorizationWithoutSpringSecurityApplication {

//    @Autowired
//    private RoleAccessService roleAccessService;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private AppUserRoleService appUserRoleService;
//    @Autowired
//    private AppUserService appUserService;


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationAuthorizationWithoutSpringSecurityApplication.class, args);
    }


//    @Bean
//    public CommandLineRunner runner() {
//        return args -> {
//
//            AppUser appUser =
//                    new AppUser("admin", "admin", "admin.021@gmail.com", "001", "123");
//            appUserService.saveUser(appUser);
//            AppUser appUser1 =
//                    new AppUser("expert", "expert", "expert.021@gmail.com", "002", "123");
//            appUserService.saveUser(appUser1);
//
//            AppUser appUser2 =
//                    new AppUser("member", "member", "member.021@gmail.com", "003", "123");
//            appUserService.saveUser(appUser2);
//
//
//
//            Role role1 = new Role("admin");
//            roleService.save(role1);
//            Role role2 = new Role("expert");
//            roleService.save(role2);
//            Role role3 = new Role("member");
//            roleService.save(role3);
//
//
//            AppUserRole appUserRole = new AppUserRole();
//            appUserRoleService.save(appUserRole);
//            appUserRole = appUserRoleService.findOne(appUserRole.getId());
//            appUserRole.setRolename(role1.getRoleName());
//            appUserRole.setUsername(appUser.getUsername());
//            appUserRoleService.update(appUserRole);
//
//            AppUserRole appUserRole1 = new AppUserRole();
//            appUserRoleService.save(appUserRole1);
//            appUserRole1 = appUserRoleService.findOne(appUserRole1.getId());
//            appUserRole1.setRolename(role2.getRoleName());
//            appUserRole1.setUsername(appUser1.getUsername());
//            appUserRoleService.update(appUserRole1);
//
//
//            AppUserRole appUserRole2 = new AppUserRole();
//            appUserRoleService.save(appUserRole2);
//            appUserRole2 = appUserRoleService.findOne(appUserRole2.getId());
//            appUserRole2.setRolename(role3.getRoleName());
//            appUserRole2.setUsername(appUser2.getUsername());
//            appUserRoleService.update(appUserRole2);
//
//
//
//
//
//
////    can not request to these api's
//            RoleAccess roleAccess7 = new RoleAccess("/api/v1/s/expert", role2.getRoleName(), "PUT");
//
//            RoleAccess roleAccess8 = new RoleAccess("/api/v1/s/member", role3.getRoleName(), "POST");
//            RoleAccess roleAccess10 = new RoleAccess("/api/v1/s/member",role3.getRoleName(), "PUT");
//            RoleAccess roleAccess9 = new RoleAccess("/api/v1/s/expert", role3.getRoleName(), "GET");
//            RoleAccess roleAccess5 = new RoleAccess("/api/v1/s/expert", role3.getRoleName(), "POST");
//            RoleAccess roleAccess6 = new RoleAccess("/api/v1/s/expert", role3.getRoleName(), "PUT");
//
//
//
//            roleAccessService.save(roleAccess5);
//            roleAccessService.save(roleAccess6);
//            roleAccessService.save(roleAccess7);
//            roleAccessService.save(roleAccess8);
//            roleAccessService.save(roleAccess9);
//            roleAccessService.save(roleAccess10);
//
//
//        };
//    }


}
