package com.kd.pocs.refreshtoken.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author kuldeep
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
