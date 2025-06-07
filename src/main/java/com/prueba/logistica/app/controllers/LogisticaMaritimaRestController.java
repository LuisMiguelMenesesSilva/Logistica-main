package com.prueba.logistica.app.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.logistica.app.entities.LogisticaMaritima;
import com.prueba.logistica.app.services.ILogisticaMaritimaService;
import com.prueba.logistica.app.utilities.Common;

@RestController
@RequestMapping("/api")
public class LogisticaMaritimaRestController {

	@Autowired
	private ILogisticaMaritimaService logisticaMservice;
	
	public static final double DESCUENTO_LOGISTICA_M = 0.03;
	
	/**
	 * {@Resumen -metodo que devuelve la lista de logistica-maritimas almacenadas en la base de datos.}
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	 */
	@GetMapping("/logistica-maritima")
	public List<LogisticaMaritima>show() {
		return logisticaMservice.findAllLogisticaM();
	}
	
	
	/**
	 * 
	 * {@Resumen - metodo realiza la busqueda de un objeto logistica maritima en la base de datos mediante el id del 
	 * 			    objeto logistica maritima recibido en la peticion y devuelve un objeto ResponseEntity con el objeto 
	 * 				logistica maritima resultado de la busqueda realizada.}
	 *
	 * @param {id} identificador de la bodega.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/logistica-maritima/{id}")
	public ResponseEntity<?> getLogisticaM(@PathVariable Long id) {
		
		LogisticaMaritima logisticaMaritima = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			logisticaMaritima = logisticaMservice.findLogisticaMById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(logisticaMaritima == null) {
			response.put("mensaje", "Logistica maritima con  ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<LogisticaMaritima>(logisticaMaritima, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * {@Resumen - metodo que guarda un objeto de tipo logistica maritima en la base de datos posterios a la validacion de todos 
	 * los atributos del objeto logistica maritima.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param logisticaM objeto tipo logistica maritima enviado en la petición http con los datos a modificar.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto logistica maritima.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/logistica-maritima")
	public ResponseEntity<?> saveLogisticaM(@Valid @RequestBody LogisticaMaritima logisticaM,BindingResult result){
		
		LogisticaMaritima logisticaMNew = null;
		Map<String,Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El Campo '" + err.getField() +"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			if(logisticaM.getCantidad() > 10) {
				BigDecimal precioNormal = logisticaM.getPrecioEnvio();
				logisticaM.setPrecioNormal(precioNormal);
				logisticaM.setPrecioEnvio(Common.aplicarDescuento(precioNormal,DESCUENTO_LOGISTICA_M));
				logisticaM.setDescuento(Common.calcularDescuentoLogisticaT(precioNormal,DESCUENTO_LOGISTICA_M));
			}else {
				logisticaM.setPrecioNormal(logisticaM.getPrecioEnvio());
				logisticaM.setPrecioEnvio(logisticaM.getPrecioEnvio());
				logisticaM.setDescuento(new BigDecimal(0.00));
			}
			logisticaMNew = logisticaMservice.saveLogisticaM(logisticaM);
			} 
		catch (DataAccessException e) {
			response.put("mensaje","Error al guardar Logistica maritima en la base de datos");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","Logistica maritima guardada con éxito!");
		response.put("logisticaM", logisticaMNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * {@Resumen - metodo que recibe un objeto logistica maritima y su identificador para actualiza los datos del objeto
	 *  logistica maritima en la base de datos posterios a la validacion de todos los atributos del objeto.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param logisticaM objeto tipo logistica maritima enviado en la petición http con los datos a modificar.
	 * @param id identificador unico del obejto logistica maritima.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto logistica maritima.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/logistica-maritima/{id}")
	public ResponseEntity<?> updateLogisticaM(@Valid @RequestBody LogisticaMaritima logisticaM,@PathVariable Long id,BindingResult result){
	
		LogisticaMaritima currentLogisticaMaritima = logisticaMservice.findLogisticaMById(id);
		LogisticaMaritima updatedLogisticaMaritima = null;
		Map<String,Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El Campo '" + err.getField() +"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentLogisticaMaritima == null) {
			response.put("mensaje", "Error: no se pudo editar, la logistica con ID :"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try 
		{
			currentLogisticaMaritima.setCantidad(logisticaM.getCantidad());
			if(logisticaM.getCantidad() > 10) {
				BigDecimal precioNormal = logisticaM.getPrecioEnvio();
				currentLogisticaMaritima.setPrecioNormal(precioNormal);
				currentLogisticaMaritima.setPrecioEnvio(Common.aplicarDescuento(precioNormal,DESCUENTO_LOGISTICA_M));
				currentLogisticaMaritima.setDescuento(Common.calcularDescuentoLogisticaT(precioNormal,DESCUENTO_LOGISTICA_M));
			}else {
				currentLogisticaMaritima.setPrecioNormal(logisticaM.getPrecioEnvio());
				currentLogisticaMaritima.setPrecioEnvio(logisticaM.getPrecioEnvio());
				currentLogisticaMaritima.setDescuento(new BigDecimal(0.00));
			}
			currentLogisticaMaritima.setFechaRegistro(logisticaM.getFechaRegistro());
			currentLogisticaMaritima.setFechaEntrega(logisticaM.getFechaEntrega());
			currentLogisticaMaritima.setNumeroFlota(logisticaM.getNumeroFlota());
			currentLogisticaMaritima.setNumeroGuia(logisticaM.getNumeroGuia());
			currentLogisticaMaritima.setCliente(logisticaM.getCliente());
			currentLogisticaMaritima.setProducto(logisticaM.getProducto());
			currentLogisticaMaritima.setPuerto(logisticaM.getPuerto());
			updatedLogisticaMaritima = logisticaMservice.saveLogisticaM(currentLogisticaMaritima);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la logistica maritima de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
		response.put("mensaje", "Logistica maritima actualizada con éxito!");
		response.put("logisticaM",updatedLogisticaMaritima );
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * {@Resumen - metodo que elimina un objeto logistica maritima de la base de datos mediante su identificador enviado en la peticion.}
	 *
	 * @param id identificador unico del objeto logistica maritima.
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/logistica-maritima/{id}")
	public ResponseEntity<?> deleteLogisticaT(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		LogisticaMaritima currentLogisticaMaritima = logisticaMservice.findLogisticaMById(id);
		
		if(currentLogisticaMaritima == null) {
			response.put("mensaje", "Error: no se pudo eliminar, logistica con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try {
			logisticaMservice.deleteLogisticaM(currentLogisticaMaritima);
			} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la logistica maritima de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Logistica maritima eliminada con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}
