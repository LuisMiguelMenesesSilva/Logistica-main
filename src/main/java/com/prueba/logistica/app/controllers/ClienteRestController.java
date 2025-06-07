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

import com.prueba.logistica.app.entities.Cliente;
import com.prueba.logistica.app.services.IClienteService;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	
	
	/**
	 * {@Resumen -metodo que devuelve la lista de clientes almacenados en la base de datos.}
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	 */
	@GetMapping("/clientes")
	public List<Cliente>show() {
		return clienteService.findAllClientes();
	}

	/**
	 * 
	 * {@Resumen - metodo realiza la busqueda de un cliente en la base de datos mediante el id del cliente y devuelve
	 *  un objeto ResponseEntity con el objeto cliente resultado de la busqueda realizada.}
	 * @param {id} identificador del cliente.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> getCliente(@PathVariable Long id) {
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findClienteById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	/**
	 * 
	 * {@Resumen - metodo que guarda un cliente en la base de datos posterios a la validacion de todos los atributos del cliente.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param cliente objeto cliente enviado en la petición http.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto cliente.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/clientes")
	public ResponseEntity<?> saveCliente(@Valid @RequestBody Cliente cliente,BindingResult result){
		
		Cliente clienteNew = null;
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
			  clienteNew = clienteService.saveCliente(cliente);
			} 
		catch (DataAccessException e) {
			response.put("mensaje","Error al guardar el cliente en la base de datos");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido guardado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que recibe un cliente y su identificador para actualiza los datos de un cliente 
	 * 	en la base de datos posterios a la validacion de todos los atributos del cliente.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param cliente objeto tipo cliente enviado en la petición http con los datos a modificar.
	 * @param id identificador unico del cliente.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto cliente.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente,@PathVariable Long id,BindingResult result){
	
		Cliente currentCliente = clienteService.findClienteById(id);
		Cliente updatedCliente = null;
		Map<String,Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El Campo '" + err.getField() +"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentCliente == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try 
		{
			currentCliente.setNombre(cliente.getNombre());
			currentCliente.setEmail(cliente.getEmail());
			currentCliente.setTelefono(cliente.getTelefono());
			updatedCliente = clienteService.saveCliente(currentCliente);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente",updatedCliente );
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que elimina un cliente de la base de datos mediante su identificador enviado en la peticion.}
	 *
	 * @param id identificador unico del cliente.
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		Cliente currentCliente = clienteService.findClienteById(id);
		
		if(currentCliente == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el cliente con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try {
				clienteService.deleteCliente(currentCliente);
			} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Cliente eliminado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
}
