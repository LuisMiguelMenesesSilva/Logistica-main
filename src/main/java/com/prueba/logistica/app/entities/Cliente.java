package com.prueba.logistica.app.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name="clientes")
@Data
public class Cliente implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cliente;
	private String nombre;
	private String direccion;
	@Email(message="no es una dirección de correo bien formada")
	@Column(nullable=false, unique=true)
	private String email;
	private String telefono;
	

	@JsonIgnoreProperties(value = {"cliente","hibernateLazyInitializer", "handler"},allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente",cascade = CascadeType.ALL)
	private java.util.List<LogisticaTerrestre> logisticaTs ;


	@JsonIgnoreProperties(value = {"cliente","hibernateLazyInitializer", "handler"},allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente",cascade = CascadeType.ALL)
	private java.util.List<LogisticaMaritima> logisticaMs ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
