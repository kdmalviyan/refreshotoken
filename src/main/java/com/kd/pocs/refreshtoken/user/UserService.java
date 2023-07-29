package com.kd.pocs.refreshtoken.user;

import java.util.List;

/**
 * @author kuldeep
 */
public interface UserService {
    User findByUsername(String username);

    User save(User user);

    List<User> findAll();
}
