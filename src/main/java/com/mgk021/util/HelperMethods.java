package com.mgk021.util;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mgk021.model.entity.AppUser;
import com.mgk021.model.entity.AppUserRole;
import com.mgk021.model.service.AppUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HelperMethods {


    @Autowired
    private AppUserRoleService appUserRoleService;


    public String createToken(AppUser appUser, boolean rememberMe) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode roles = mapper.createArrayNode();
        System.out.println("create" + appUser.getUsername());
        List<AppUserRole> appUserRoleList = appUserRoleService.findByWhere("o.username='" + appUser.getUsername() + "'");
        System.out.println(appUserRoleList.size());
        for (AppUserRole appUserRole : appUserRoleList) {
            roles.add(appUserRole.getRolename());
        }
        ObjectNode detail = mapper.createObjectNode();
        detail.set("userRoles", roles);
        if (rememberMe) {
            detail.put("expireTime", LocalDateTime.now().plusMinutes(7 * 24 * 60 * 60).toString());
        } else {
            detail.put("expireTime", LocalDateTime.now().plusMinutes(2 * 60 * 60).toString());
        }
        detail.put("userName", appUser.getUsername());
        detail.put("userId", appUser.getId());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(detail);
        String token = EncoderUtil.getAESEncrypt(json);
        return token;
    }

    public String extractTokenFromCookie(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    public String decodeToken(String token) throws Exception {
        return EncoderUtil.getAESDecrypt(token);
    }

    public Map convertTokenToMap(String token) throws Exception {
        if (token == null) return null;
        String tokenContent = decodeToken(token);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(tokenContent, new TypeReference<Map<String, Object>>() {
        });
        return map;
    }

    public Boolean isTokenExpired(String token) throws Exception {
        LocalDateTime localDateTime = extractExpireTime(token);
        return localDateTime.isBefore(LocalDateTime.now());
    }

    public LocalDateTime extractExpireTime(String token) throws Exception {
        return LocalDateTime.parse((CharSequence) convertTokenToMap(token).get("expireTime"));
    }

    public Long extractUserId(String token) throws Exception {
        return ((Number) convertTokenToMap(token).get("userId")).longValue();
    }

    public Long extractUserIdFromRequest(HttpServletRequest request) throws Exception {
        String token = extractTokenFromCookie(request);
        return ((Number) convertTokenToMap(token).get("userId")).longValue();
    }

    public List extractRoleNames(String token) throws Exception {
        return (List) convertTokenToMap(token).get("userRoles");
    }

    public boolean hasRole(List roles, String roleName) {
        if (roles.indexOf(roleName) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public String extractUsername(String token) throws Exception {
        return (String) convertTokenToMap(token).get("userName");
    }

    public Cookie deleteCookie() {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }


    public Object extractErrors(BindingResult bindingResult) {
        Map errors = new HashMap();
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }


    public String slugify(String input) {
        String nowhitespace = input.replaceAll(" ", "-");
        return nowhitespace;
    }



}
