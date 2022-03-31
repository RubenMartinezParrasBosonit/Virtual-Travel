package com.ruben.backempresa.user.application;

import com.ruben.backempresa.role.domain.Role;
import com.ruben.backempresa.role.infraestructure.repository.RoleRepository;
import com.ruben.backempresa.user.domain.MyUser;
import com.ruben.backempresa.user.infraestructure.repository.MyUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MyUserServiceImpl implements MyUserService, UserDetailsService {

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //Overrrided de UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);

        if(myUser == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        myUser.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(myUser.getUsername()
                , myUser.getPassword(), authorities);
    }

    @Override
    public MyUser saveUser(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return myUserRepository.save(myUser);
    }

    @Override
    public Role saveRol(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        MyUser myUser = myUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        myUser.getRoles().add(role);

    }

    @Override
    public MyUser getUser(String username) {
        return myUserRepository.findByUsername(username);
    }

    @Override
    public List<MyUser> getUsers() {
        return myUserRepository.findAll();
    }


}
