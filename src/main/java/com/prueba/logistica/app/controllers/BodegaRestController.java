package com.prueba.logistica.app.controllers;

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

import com.prueba.logistica.app.entities.Bodega;
import com.prueba.logistica.app.services.IBodegaService;

@RestController
@RequestMapping("/api")
public class BodegaRestController {

	@Autowired
	private IBodegaService bodegaService;
	
	/**
	 * {@Resumen -metodo que devuelve la lista de bodegas almacenadas en la base de datos.}
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	 */
	@GetMapping("/bodegas")
	public List<Bodega>show() {
		return bodegaService.findAllBodegas();
	}

	/**
	 * 
	 * {@Resumen - metodo realiza la busqueda de una bodega en la base de datos mediante el id de la bodega recibido en la peticion
	 * 			   y devuelve un objeto ResponseEntity con el objeto bodega resultado de la busqueda realizada.}
	 * @param {id} identificador de la bodega.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/bodegas/{id}")
	public ResponseEntity<?> getBodega(@PathVariable Long id) {
		
		Bodega bodega = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			bodega = bodegaService.findBodegaById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(bodega == null) {
			response.put("mensaje", "El Bodega con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Bodega>(bodega, HttpStatus.OK);
	}
	
	/**
	 * 
	 * {@Resumen - metodo que guarda una bodega en la base de datos posterios a la validacion de todos los atributos de la bodega.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param bodega objeto bodega enviado en la petición http.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto bodega.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	@Secured("ROLE_ADMIN")
	@PostMapping("/Bodegas")
	public ResponseEntity<?> saveBodega(@Valid @RequestBody Bodega bodega,BindingResult result){
		
		Bodega bodegaNew = null;
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
			  bodegaNew = bodegaService.saveBodega(bodega);
			} 
		catch (DataAccessException e) {
			response.put("mensaje","Error al guardar el Bodega en la base de datos");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El Bodega ha sido guardado con éxito!");
		response.put("Bodega", bodegaNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que recibe un objeto bodega y su identificador para actualiza los datos de una bodega 
	 * 	en la base de datos posterios a la validacion de todos los atributos del obejto bodega.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param bodega objeto tipo bodaga enviado en la petición http con los datos a modificar.
	 * @param id identificador unico de la bodega.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto bodega.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/Bodegas/{id}")
	public ResponseEntity<?> updateBodega(@Valid @RequestBody Bodega bodega,@PathVariable Long id,BindingResult result){
	
		Bodega currentBodega = bodegaService.findBodegaById(id);
		Bodega updatedBodega = null;
		Map<String,Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El Campo '" + err.getField() +"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentBodega == null) {
			response.put("mensaje", "Error: no se pudo editar, el Bodega con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try 
		{
			currentBodega.setNombre_bodega(bodega.getNombre_bodega());
			currentBodega.setUbicacion(bodega.getUbicacion());
			updatedBodega = bodegaService.saveBodega(currentBodega);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Bodega de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
		response.put("mensaje", "El Bodega ha sido actualizado con éxito!");
		response.put("Bodega",updatedBodega );
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que elimina una bodega de la base de datos mediante su identificador enviado en la peticion.}
	 *
	 * @param id identificador unico de la bodega.
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/Bodegas/{id}")
	public ResponseEntity<?> deleteBodega(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		Bodega currentBodega = bodegaService.findBodegaById(id);
		
		if(currentBodega == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el Bodega con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try {
				bodegaService.deleteBodega(currentBodega);
			} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el Bodega de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Bodega eliminado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
}
