package com.mgk021.controller;


import com.mgk021.model.entity.AppUser;
import com.mgk021.model.entity.helperentity.AuthRequest;
import com.mgk021.model.service.AppUserService;
import com.mgk021.util.EncoderUtil;
import com.mgk021.util.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AppUserService service;
    @Autowired
    private HelperMethods methods;

    @RequestMapping(value = "/a" , method = RequestMethod.GET)
    public void test(){
        System.out.println("asdasdsad");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        System.out.println(authRequest.getPassword());
        System.out.println(authRequest.getUsername());
        AppUser appUser = service.findByUserNameAndPassword(authRequest.getUsername(), EncoderUtil.getSHA256(authRequest.getPassword()));
        if (appUser != null) {
            String token = methods.createToken(appUser, authRequest.isRememberMe());
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            //cookie.setSecure(true);
            if (authRequest.isRememberMe()) {
                cookie.setMaxAge(7 * 24 * 60 * 60);
            } else {
                cookie.setMaxAge(2 * 60 * 60);
            }
            response.addCookie(cookie);
            response.setStatus(200);
        } else {
            //401 Unauthorized
            response.setStatus(401);
        }
    }

    @RequestMapping(value = "/logout")
    public void logOut(HttpServletResponse response) throws Exception {
        Cookie cookie = methods.deleteCookie();
        response.addCookie(cookie);
    }


    @RequestMapping(value = "/isValid")
    public Object isValid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap<String, String>();
        try {
            String token = methods.extractTokenFromCookie(request);
            //String tokenContent = EncoderUtil.getAESDecrypt(token);
            if (token == null) {
                map.put("success", false);
                return map;
            }
            Map mm = methods.convertTokenToMap(token);
            Long userId = ((Number) mm.get("userId")).longValue();
            if (userId == null) {
                response.setStatus(403);
                map.put("success", false);
                return map;
            }
            AppUser appUser = service.findOne(userId);
            Map userInf = new HashMap<String, String>();
            userInf.put("userRoles", methods.convertTokenToMap(token).get("userRoles"));
            userInf.put("name", appUser.getName());
            userInf.put("family", appUser.getFamily());
            userInf.put("email", appUser.getEmail());
            userInf.put("username", appUser.getUsername());
            userInf.put("id", appUser.getId());
            map.put("userInfo", userInf);
            map.put("success", true);
            return map;
            //  }
        } catch (Exception e) {
            map.put("success", false);
            e.printStackTrace();
            return map;
        }
    }

}
