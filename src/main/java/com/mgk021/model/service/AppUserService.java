package com.mgk021.model.service;



import com.mgk021.model.entity.AppUser;
import com.mgk021.model.repository.GenericRepository;
import com.mgk021.util.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppUserService {

    @Autowired
    private GenericRepository<Long, AppUser, String> repo;
    @PersistenceContext
    private EntityManager entityManager;

    public void saveUser(AppUser appUser) throws Exception {
        String hashPass = EncoderUtil.getSHA256(appUser.getPassword());
        appUser.setPassword(hashPass);
        repo.save(appUser);
    }

    public void updateUser(AppUser appUser) {
        repo.update(appUser);
    }

    public void removeUser(AppUser appUser) {
        repo.delete(appUser);
    }

    public AppUser findOne(Long id) {
        AppUser appUser = repo.findOne(AppUser.class, id);
        return appUser;
    }

    public AppUser findOnePure(Long id) {
        AppUser appUser = repo.findOne(AppUser.class, id);
        return appUser;
    }

    public List<AppUser> findAllUser() {
        return repo.findAll(AppUser.class);
    }

    public List<AppUser> findByWhere(String s) {
        return repo.findByWhere(AppUser.class, s);
    }

    public AppUser findByUserNameAndPassword(String username, String password) {
        Query query = entityManager.createNamedQuery("userExist");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List result = query.getResultList();
        entityManager.close();
        if (!result.isEmpty()) {
            return (AppUser) result.get(0);
        } else {
            return null;
        }
    }

    public AppUser findByEmail(String email) {
        List<AppUser> appUser = findByWhere("o.email='" + email + "'");
        if (appUser.size() > 0) return appUser.get(0);
        else return null;
    }

    public boolean isUnique(String username) {
        Query query = entityManager.createNamedQuery(AppUser.IS_UNIQUE)
                .setParameter("username", username);
        int count = ((Number) query.getSingleResult()).intValue();
        if (count == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean emailUnique(String email) {
        Query query = entityManager.createNamedQuery(AppUser.EMAIL_UNIQUE)
                .setParameter("email", email);
        int count = ((Number) query.getSingleResult()).intValue();
        if (count == 0) {
            return true;
        } else {
            return false;
        }

    }



    public Object getDataByPaging(int pageNo, int pageSize, String title) {
        pageSize = Math.min(pageSize, 60);
        Map map = new HashMap<String, Object>();
        int firstResult = (pageNo - 1) * pageSize;
        Query query;
        if (title == null) {
            query = entityManager.createNamedQuery(AppUser.ALL);
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            List<AppUser> appUserList = query.getResultList();
            List<AppUser> appUsers = new ArrayList<>();
            map.put("data", appUsers);
            int count = ((Number) entityManager.createNamedQuery(AppUser.COUNT_ALL).getSingleResult()).intValue();
            map.put("count", count);
            return map;
        } else {
            query = entityManager.createNamedQuery(AppUser.NAME_FILTER);
            query.setParameter("title", "%" + title + "%");
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            List<AppUser> appUserList = query.getResultList();
            List<AppUser> appUsers = new ArrayList<>();
            map.put("data", appUsers);
            int count = ((Number) entityManager.createNamedQuery(AppUser.COUNT_ALL_NAME_FILTER)
                    .setParameter("title", "%" + title + "%")
                    .getSingleResult()).intValue();
            map.put("count", count);
            return map;
        }
    }


}
