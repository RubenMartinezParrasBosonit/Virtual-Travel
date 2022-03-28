package com.ruben.backempresa.correo.infraestructure.repository;

import com.ruben.backempresa.correo.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorreoRepository extends JpaRepository<Correo, Integer> {

    List<Correo> findByEmail(String email);
}
