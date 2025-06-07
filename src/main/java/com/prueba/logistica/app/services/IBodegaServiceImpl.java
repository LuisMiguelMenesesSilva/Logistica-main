package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.logistica.app.entities.Bodega;
import com.prueba.logistica.app.repositories.BodegaRepository;

@Service
public class IBodegaServiceImpl implements IBodegaService {

	@Autowired
	private BodegaRepository bodegaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Bodega> findAllBodegas() {
		return bodegaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Bodega findBodegaById(Long id) {
		return bodegaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Bodega saveBodega(Bodega bodega) {
		return bodegaRepository.save(bodega);
	}

	@Override
	@Transactional
	public void deleteBodega(Bodega bodega) {
		bodegaRepository.delete(bodega);
	}
}
