package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.logistica.app.entities.Puerto;
import com.prueba.logistica.app.repositories.PuertoRepository;

@Service
public class IPuertoServiceImpl implements IPuertoService {

	@Autowired
	private PuertoRepository puertoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Puerto> findAllPuertos() {
		return puertoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Puerto findPuertoById(Long id) {
		return puertoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Puerto savePuerto(Puerto puerto) {
		return puertoRepository.save(puerto);
	}

	@Override
	@Transactional
	public void deletePuerto(Puerto puerto) {
		puertoRepository.delete(puerto);
	}
}
