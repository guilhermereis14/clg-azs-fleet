package com.azlogistica.azslog.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azlogistica.azslog.api.model.Veiculo;
import com.azlogistica.azslog.api.repository.VeiculoRepository;
import com.azlogistica.azslog.api.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private VeiculoService veiculoService;
	
	/* Listando todos os veículos cadastrados */
	
	@GetMapping
	public List<Veiculo> listar(){
		return veiculoRepository.findAll();
	}
	
	/* Buscando veículos por código - Retornará status 404 caso não existir */
	
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		Optional veiculo = veiculoRepository.findById(codigo);
		return veiculo.isPresent() ?
				ResponseEntity.ok(veiculo.get()) : ResponseEntity.notFound().build(); 
	}
	
	/* Filtrando veículo por placa */
	@GetMapping(value = "/placa/{placa}")
	public Veiculo findByPlaca(@Valid @PathVariable final String placa) {
		return veiculoRepository.findByPlaca(placa);
	}
	
	/* Cadastrando veículos */
	@PostMapping
	public ResponseEntity<Veiculo> criarVeilculo (@Valid @RequestBody Veiculo veiculo, HttpServletResponse response) {
		
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		
		/* retornando a informação salva no location requisição */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(veiculoSalvo.getId()).toUri();
		//response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(veiculoSalvo);
		
				
	}
	
	/* Atualizar veiculo */
	@PutMapping("/{codigo}")
	public ResponseEntity<Veiculo> atualizar(@Valid @PathVariable Long codigo, @RequestBody Veiculo veiculo){
		
		Veiculo veiculoSalvo = veiculoService.atualizar(codigo, veiculo);
			
		return ResponseEntity.ok(veiculoSalvo); 
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long codigo) {
		veiculoRepository.deleteById(codigo);
	}
	
	
	
	
	
	
	

}
