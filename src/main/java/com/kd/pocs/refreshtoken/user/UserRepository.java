package com.kd.pocs.refreshtoken.user;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kuldeep
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
