package com.prueba.logistica.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.logistica.app.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
