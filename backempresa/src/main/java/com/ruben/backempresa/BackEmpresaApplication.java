package com.ruben.backempresa;

import com.ruben.backempresa.role.domain.Role;
import com.ruben.backempresa.user.application.MyUserService;
import com.ruben.backempresa.user.domain.MyUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BackEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEmpresaApplication.class, args);
	}

	@Bean
	CommandLineRunner run(MyUserService myUserService){
		return args -> {
			myUserService.saveRol(new Role(null, "ADMIN"));
			myUserService.saveRol(new Role(null, "USER"));

			myUserService.saveUser(new MyUser(null, "Administrador", "administrador"
					, "1234", new ArrayList<>()));
			myUserService.saveUser(new MyUser(null, "Usuario", "usuario"
					, "1234", new ArrayList<>()));

			myUserService.addRoleToUser("administrador", "ADMIN");
			myUserService.addRoleToUser("usuario", "USER");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
