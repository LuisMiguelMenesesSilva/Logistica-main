package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.logistica.app.entities.LogisticaTerrestre;
import com.prueba.logistica.app.repositories.LogisticaTerrestreRepository;

@Service
public class ILogisticaTerrestreServiceImpl implements ILogisticaTerrestreService {

	@Autowired
	private LogisticaTerrestreRepository logisticaTrepository; 
	
	@Override
	@Transactional(readOnly = true)
	public List<LogisticaTerrestre> findAllLogisticaT() {
		return logisticaTrepository.findAll();
	}

	@Override
	@Transactional
	public LogisticaTerrestre saveLogisticaT(LogisticaTerrestre logisticaT) {
		return logisticaTrepository.save(logisticaT);
	}

	@Override
	@Transactional(readOnly = true)
	public LogisticaTerrestre findLogisticaTById(Long id) {
		return logisticaTrepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteLogisticaT(LogisticaTerrestre logisticaT) {
		logisticaTrepository.delete(logisticaT);
	}

}
