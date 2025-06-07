package com.prueba.logistica.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.logistica.app.entities.Cliente;
import com.prueba.logistica.app.repositories.ClienteRepository;

@Service
public class IClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAll();
	}

	@Override
	@Transactional
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findClienteById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteCliente(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

}
