package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.LogisticaTerrestre;

public interface ILogisticaTerrestreService {

	public List<LogisticaTerrestre> findAllLogisticaT();
	public LogisticaTerrestre saveLogisticaT(LogisticaTerrestre logisticaT);
	public LogisticaTerrestre findLogisticaTById(Long id);
	public void deleteLogisticaT(LogisticaTerrestre logisticaT);
}
