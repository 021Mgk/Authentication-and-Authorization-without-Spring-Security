package com.mgk021.config;



import com.mgk021.model.entity.AppUserRole;
import com.mgk021.model.entity.RoleAccess;
import com.mgk021.model.service.AppUserRoleService;
import com.mgk021.model.service.RoleAccessService;
import com.mgk021.util.HelperMethods;
import com.mgk021.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class PrivateRater implements Filter {

    @Autowired
    private RoleAccessService roleAccessService;
    @Autowired
    private AppUserRoleService appUserRoleService;
    @Autowired
    private HelperMethods methods;


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("RATER>> URI>> " + request.getRequestURI() + ">>METHOD>>" + request.getMethod() + ">>STATUS>>" + response.getStatus());
        String token = methods.extractTokenFromCookie(request);

        if (token != null) {
            try {
                boolean validToken = methods.isTokenExpired(token);
                if (validToken) {
                    response.setStatus(401);
                    response.addCookie(methods.deleteCookie());
                    return;
                }
                List userRoleNames = methods.extractRoleNames(token);
                String userName = methods.extractUsername(token);
                if (userRoleNames.contains("admin")) {
                    System.out.println("U ARE ADMIN");
                    LoggedUser.logIn(userName);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    List<AppUserRole> appUserRolesList = appUserRoleService.findByWhere("o.username=" + userName);
                    for (AppUserRole appUserRole : appUserRolesList) {
                        List<RoleAccess> roleAccessList = roleAccessService.findByWhere("o.rolename='" + appUserRole.getRolename() + "'");
                        for (RoleAccess access : roleAccessList) {
                            if (request.getRequestURI().equals(access.getAddress()) &&
                                    request.getMethod().equals(access.getMethod())) {
                                response.setStatus(403);
                                return;
                            }
                        }

                    }
                    LoggedUser.logIn(userName);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LoggedUser.logOut();
            }
        } else {
            response.setStatus(403);
        }

    }


    public void destroy() {

    }
}

