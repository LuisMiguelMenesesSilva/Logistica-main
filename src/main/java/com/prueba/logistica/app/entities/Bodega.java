package com.prueba.logistica.app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bodegas")
@Data
public class Bodega implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_bodega;
	private String nombre_bodega;
	private String ubicacion;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
