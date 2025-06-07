package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.logistica.app.entities.Producto;
import com.prueba.logistica.app.repositories.ProductoRepository;

@Service
public class IProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAllProductos() {
		return productoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto saveProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	@Transactional
	public void deleteProducto(Producto producto) {
		productoRepository.delete(producto);
	}
}
