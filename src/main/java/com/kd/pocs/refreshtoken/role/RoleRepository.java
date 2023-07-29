package com.kd.pocs.refreshtoken.role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kuldeep
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
