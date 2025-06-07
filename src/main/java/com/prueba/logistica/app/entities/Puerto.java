package com.prueba.logistica.app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="puertos")
@Data
public class Puerto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_puerto;
	private String nombre_puerto;
	private String ubicacion;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
