package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	@Query(value = "SELECT * FROM ejercicio_java.Users where email = ?1", nativeQuery = true)
	Usuario getByEmail(String email);
}

