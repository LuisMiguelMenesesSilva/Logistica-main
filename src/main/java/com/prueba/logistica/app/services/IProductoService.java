package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.Producto;

public interface IProductoService {

	public List<Producto> findAllProductos();
	public Producto findProductoById(Long id);
	public Producto saveProducto(Producto producto);
	public void deleteProducto(Producto producto);
}
