package com.prueba.logistica.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.logistica.app.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
}
