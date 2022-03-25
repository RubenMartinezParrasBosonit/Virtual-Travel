package com.ruben.backempresa.role.infraestructure.repository;

import com.ruben.backempresa.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
