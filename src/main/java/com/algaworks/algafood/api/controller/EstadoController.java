package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.listar();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId) {
		return estadoRepository.buscar(estadoId);
	}
}
