package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.Bodega;

public interface IBodegaService {

	public List<Bodega> findAllBodegas();
	public Bodega findBodegaById(Long id);
	public Bodega saveBodega(Bodega bodega);
	public void deleteBodega(Bodega bodega);
}
