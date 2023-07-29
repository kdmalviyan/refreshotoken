package com.kd.pocs.refreshtoken.role;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author kuldeep
 */
@Data
@Entity
@Table(name = "tb_roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Override
    public String getAuthority() {
        return name;
    }
}
