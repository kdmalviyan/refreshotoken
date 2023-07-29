package com.kd.pocs.refreshtoken.role;

/**
 * @author kuldeep
 */
public interface RoleService {
    Role findByName(String name);

    Role save(Role role);
}
