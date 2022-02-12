package com.mgk021.model.service;



import com.mgk021.model.entity.Role;
import com.mgk021.model.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private GenericRepository<Long , Role, String> repo;

    public void save(Role role) {
        repo.save(role);
    }

    public void update(Role role) {
        repo.update(role);
    }

    public void remove(Role role) {
        repo.delete(role);
    }

    public Role findOne(Long id) { return repo.findOne(Role.class, id); }

    public List<Role> findAll() {
        return repo.findAll(Role.class);
    }

    public List<Role> findByWhere(String s) {
        return repo.findByWhere(Role.class, s);
    }

}
