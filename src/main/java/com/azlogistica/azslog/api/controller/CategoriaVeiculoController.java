package com.azlogistica.azslog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azlogistica.azslog.api.model.CategoriaVeiculo;
import com.azlogistica.azslog.api.repository.CategoriaVeiculoRepository;

@RestController
@RequestMapping("/categoriaveiculos")
public class CategoriaVeiculoController {
	
	@Autowired
	private CategoriaVeiculoRepository categoriaVeiculoRepository;
	
	/* Listando todas as categorias de veículos cadastradas */
	@GetMapping
	public List <CategoriaVeiculo> listar(){
		return categoriaVeiculoRepository.findAll();
	}
	
	

}
