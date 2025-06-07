package com.prueba.logistica.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.logistica.app.entities.LogisticaTerrestre;

public interface LogisticaTerrestreRepository extends JpaRepository<LogisticaTerrestre, Long> {

}
