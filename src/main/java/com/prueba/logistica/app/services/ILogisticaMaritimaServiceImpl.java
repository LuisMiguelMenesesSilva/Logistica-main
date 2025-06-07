package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.logistica.app.entities.LogisticaMaritima;
import com.prueba.logistica.app.repositories.LogisticaMaritimaRepository;

@Service
public class ILogisticaMaritimaServiceImpl implements ILogisticaMaritimaService{

	@Autowired
	private LogisticaMaritimaRepository logisticaMrepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<LogisticaMaritima> findAllLogisticaM() {
		return logisticaMrepository.findAll();
	}

	@Override
	@Transactional
	public LogisticaMaritima saveLogisticaM(LogisticaMaritima logisticaM) {
		return logisticaMrepository.save(logisticaM);
	}

	@Override
	@Transactional(readOnly = true)
	public LogisticaMaritima findLogisticaMById(Long id) {
		return logisticaMrepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteLogisticaM(LogisticaMaritima logisticaM) {
		logisticaMrepository.delete(logisticaM);
	}

	

}
