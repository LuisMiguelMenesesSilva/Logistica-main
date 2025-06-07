package com.prueba.logistica.app.services;

import java.util.List;

import com.prueba.logistica.app.entities.Cliente;

public interface IClienteService {

	public List<Cliente> findAllClientes();
	public Cliente findClienteById(Long id);
	public Cliente saveCliente(Cliente cliente);
	public void deleteCliente(Cliente cliente);
}
