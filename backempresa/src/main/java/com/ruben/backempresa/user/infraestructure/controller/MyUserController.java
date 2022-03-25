package com.ruben.backempresa.user.infraestructure.controller;

import com.ruben.backempresa.role.domain.Role;
import com.ruben.backempresa.user.application.MyUserService;
import com.ruben.backempresa.user.domain.MyUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyUserController {

    @Autowired
    MyUserService myUserService;

    @GetMapping("/user/users")
    public ResponseEntity<List<MyUser>> getUsers(){
        return ResponseEntity.ok().body(myUserService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<MyUser> saveUser(@RequestBody MyUser myUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(myUserService.saveUser(myUser));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(myUserService.saveRol(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        myUserService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
