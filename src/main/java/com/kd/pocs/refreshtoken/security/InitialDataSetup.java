package com.kd.pocs.refreshtoken.security;

import com.kd.pocs.refreshtoken.user.UserRepository;
import com.kd.pocs.refreshtoken.role.Role;
import com.kd.pocs.refreshtoken.role.RoleRepository;
import com.kd.pocs.refreshtoken.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Set;

/**
 * @author kuldeep
 */
@Configuration
public class InitialDataSetup {
    public InitialDataSetup(@Autowired UserRepository userRepository,
                            @Autowired RoleRepository roleRepository,
                            @Autowired PasswordEncoder passwordEncoder) {
        createRole(roleRepository);
        createUser(userRepository, roleRepository, passwordEncoder);
    }

    private void createUser(UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        User user = userRepository.findByUsername("admin");
        if(Objects.isNull(user)) {
            user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("Password@1"));
            user.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN")));
            userRepository.save(user);
        }
    }

    private void createRole(RoleRepository repository) {
        Role role = repository.findByName("ROLE_ADMIN");
        if(Objects.isNull(role)) {
            role = new Role();
            role.setName("ROLE_ADMIN");
            repository.save(role);
        }
    }
}
