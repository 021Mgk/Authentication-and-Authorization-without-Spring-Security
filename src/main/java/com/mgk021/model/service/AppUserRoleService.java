package com.mgk021.model.service;


import com.mgk021.model.entity.AppUserRole;
import com.mgk021.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppUserRoleService {

    @Autowired
    private GenericRepository<Long, AppUserRole, String> repo;

    public void save(AppUserRole appUserRole) {
        repo.save(appUserRole);
    }

    public void update(AppUserRole appUserRole) {
        repo.update(appUserRole);
    }

    public void remove(AppUserRole appUserRole) {
        repo.delete(appUserRole);
    }

    public AppUserRole findOne(Long id) {
        return repo.findOne(AppUserRole.class, id);
    }

    public List<AppUserRole> findAll() {
        return repo.findAll(AppUserRole.class);
    }

    public List<AppUserRole> findByWhere(String s) {
        return repo.findByWhere(AppUserRole.class, s);
    }
}
