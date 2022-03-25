package com.ruben.backempresa.user.infraestructure.repository;

import com.ruben.backempresa.user.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}
