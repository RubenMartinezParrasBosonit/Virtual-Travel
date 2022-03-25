package com.ruben.backempresa.user.application;

import com.ruben.backempresa.role.domain.Role;
import com.ruben.backempresa.user.domain.MyUser;

import java.util.List;

public interface MyUserService {
    MyUser saveUser(MyUser myUser);
    Role saveRol(Role role);
    void addRoleToUser(String username, String roleName);
    MyUser getUser(String username);
    List<MyUser> getUsers();
}
