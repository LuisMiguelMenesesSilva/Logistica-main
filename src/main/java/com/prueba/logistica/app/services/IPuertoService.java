package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.Puerto;

public interface IPuertoService {

	public List<Puerto> findAllPuertos();
	public Puerto findPuertoById(Long id);
	public Puerto savePuerto(Puerto puerto);
	public void deletePuerto(Puerto puerto);
}
