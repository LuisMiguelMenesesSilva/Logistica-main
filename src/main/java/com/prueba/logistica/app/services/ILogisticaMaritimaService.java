package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.LogisticaMaritima;

public interface ILogisticaMaritimaService {

	public List<LogisticaMaritima> findAllLogisticaM();
	public LogisticaMaritima saveLogisticaM(LogisticaMaritima logisticaM);
	public LogisticaMaritima findLogisticaMById(Long id);
	public void deleteLogisticaM(LogisticaMaritima logisticaM);
}
