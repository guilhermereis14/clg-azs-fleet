package com.azlogistica.azslog.api.controller;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azlogistica.azslog.api.model.Viagem;
import com.azlogistica.azslog.api.repository.ViagemRepository;
import com.azlogistica.azslog.api.service.ViagemService;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private ViagemService viagemService;	
	
	/*Listando todas as viagens */
	@GetMapping 
	public List<Viagem> listar(){
		return viagemRepository.findAll();
	}
	
	/* Buscando viagem por código - Retornará status 404 caso não existir */
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		Optional viagem = viagemRepository.findById(codigo);
		return viagem.isPresent() ?
				ResponseEntity.ok(viagem.get()) : ResponseEntity.notFound().build(); 
	}
	
	
	@GetMapping("/consulta/data/{data}")
	public List<Viagem> findByData(@PathVariable String data) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate novaData = LocalDate.parse(data, dateTimeFormatter);
		
		//Date novaData = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		
		return viagemRepository.dataInicioEquals(novaData);
		
	}
	
	/* Buscar viagens por motorista (nome) */
	@GetMapping("/consulta/motorista/{nome}")
	public List<Viagem> consultaViagemNomeMotorista(@PathVariable String nome ){
		return viagemRepository.motorista(nome);
	}
	
	/* Buscar viagens por placa do veículo */
	@GetMapping("/consulta/placaveiculo/{placa}")
	public List<Viagem> consultaViagemPlacaVeiculo(@PathVariable String placa ){
		return viagemRepository.placa(placa);
	}
	
	/* Cadastrando viagem */
	@PostMapping
	public ResponseEntity<Viagem> criarViagem (@Valid @RequestBody Viagem viagem, HttpServletResponse response) {
		
		Viagem viagemSalva = viagemRepository.save(viagem);
		
		/* retornando a informação salva no location requisição */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(viagemSalva.getId()).toUri();
		//response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(viagemSalva);
	}
	
	/* Atualizar viagem */
	@PutMapping("/{codigo}")
	public ResponseEntity<Viagem> atualizar(@Valid @PathVariable Long codigo, @RequestBody Viagem viagem){
		
		Viagem viagemSalva = viagemService.atualizar(codigo, viagem);
			
		return ResponseEntity.ok(viagemSalva); 
	}
	
	/*Excluir Viagem */
	@DeleteMapping("/{codigo}")
	public void remover(@PathVariable long codigo) {
		viagemRepository.deleteById(codigo);
	}
}
