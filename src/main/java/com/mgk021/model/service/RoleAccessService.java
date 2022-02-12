package com.mgk021.model.service;


import com.mgk021.model.entity.RoleAccess;
import com.mgk021.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAccessService {

    @Autowired
    private GenericRepository<Long , RoleAccess, String> repo;

    public void save(RoleAccess roleAccess) {
        repo.save(roleAccess);
    }

    public void update(RoleAccess roleAccess) {
        repo.update(roleAccess);
    }

    public void remove(RoleAccess roleAccess) {
        repo.delete(roleAccess);
    }

    public RoleAccess findOne(Long id){ return repo.findOne(RoleAccess.class , id); }

    public List<RoleAccess> findAll() {
        return repo.findAll(RoleAccess.class);
    }

    public List<RoleAccess> findByWhere(String s) {
        return repo.findByWhere(RoleAccess.class, s);
    }

}
