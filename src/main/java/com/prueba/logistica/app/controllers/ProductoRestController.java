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

import com.prueba.logistica.app.entities.Producto;
import com.prueba.logistica.app.services.IProductoService;

@RestController
@RequestMapping("/api")
public class ProductoRestController {

	@Autowired
	private IProductoService productoService;
	
	/**
	 * {@Resumen -metodo que devuelve la lista de productos almacenadas en la base de datos.}
	 * 
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	 */
	
	@GetMapping("/productos")
	public List<Producto>show() {
		return productoService.findAllProductos();
	}

	/**
	 * 
	 * {@Resumen - metodo realiza la busqueda de un producto en la base de datos mediante el id del producto recibido en la peticion
	 * 			   y devuelve un objeto ResponseEntity con el objeto producto resultado de la busqueda realizada.}
	 * @param {id} identificador del producto.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> getProducto(@PathVariable Long id) {
		
		Producto Producto = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			Producto = productoService.findProductoById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(Producto == null) {
			response.put("mensaje", "El Producto con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Producto>(Producto, HttpStatus.OK);
	}
	
	/**
	 * 
	 * {@Resumen - metodo que guarda un producto en la base de datos posterior a la validacion de todos los atributos del producto.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param producto objeto producto enviado en la petición http.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto producto.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	 */
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/productos")
	public ResponseEntity<?> saveProducto(@Valid @RequestBody Producto producto,BindingResult result){
		
		Producto ProductoNew = null;
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
			  ProductoNew = productoService.saveProducto(producto);
			} 
		catch (DataAccessException e) {
			response.put("mensaje","Error al guardar el Producto en la base de datos");
			response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El Producto ha sido guardado con éxito!");
		response.put("Producto", ProductoNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que recibe un objeto producto y su identificador para actualiza los datos de un producto 
	 * 	en la base de datos posterios a la validacion de todos los atributos del objeto producto.}
	 *
	 * @param Valid restriccion que valida las reglas de negocio definidas en cada uno de los campos del objeto.
	 * @param producto objeto tipo producto enviado en la petición http con los datos a modificar.
	 * @param id identificador unico de la producto.
	 * @param result objeto que sirve para realizar la verificacion de todos los atributos del objeto producto.
	 * 
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/Productos/{id}")
	public ResponseEntity<?> updateProducto(@Valid @RequestBody Producto producto,@PathVariable Long id,BindingResult result){
	
		Producto currentProducto = productoService.findProductoById(id);
		Producto updatedProducto = null;
		Map<String,Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El Campo '" + err.getField() +"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentProducto == null) {
			response.put("mensaje", "Error: no se pudo editar, el Producto con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try 
		{
			currentProducto.setNombre_producto(producto.getNombre_producto());
			currentProducto.setDescripcion(producto.getDescripcion());
			updatedProducto = productoService.saveProducto(currentProducto);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Producto de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
		response.put("mensaje", "El Producto ha sido actualizado con éxito!");
		response.put("Producto",updatedProducto );
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	/**
	 * 
	 * {@Resumen - metodo que elimina un producto de la base de datos mediante su identificador enviado en la peticion.}
	 *
	 * @param id identificador unico del producto.
	 * @throws DataAccessException
	 *  
	 * @author luis.meneses
	 * @version 1.0
	 * @since 2025-06-6
	*/
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/Productos/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		Producto currentProducto = productoService.findProductoById(id);
		
		if(currentProducto == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el Producto con ID:"
					.concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND); 
		}
		
		try {
				productoService.deleteProducto(currentProducto);
			} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el Producto de la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Producto eliminado con éxito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
}
