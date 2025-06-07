package com.prueba.logistica.app.services;

import com.prueba.logistica.app.entities.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
}
