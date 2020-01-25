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

import com.azlogistica.azslog.api.model.Pessoa;
import com.azlogistica.azslog.api.repository.PessoaRepository;
import com.azlogistica.azslog.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	/* Buscando pessoa por código - Retornará status 404 caso não existir */
	
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		Optional pessoa = pessoaRepository.findById(codigo);
		return pessoa.isPresent() ?
				ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build(); 
	}
	
	/* Consulta pessoa utilizando CPF */
	@GetMapping("/consulta/cpf/{cpf}")
	public Pessoa findByCpf(@Valid @PathVariable final String cpf) {
		return pessoaRepository.findByCpf(cpf);
	}
	
	/* Consulta pessoa pelo nome  */
	@GetMapping("/consulta/nome/{nome}")
	public List<Pessoa> findByNome(@PathVariable final String nome) {
		
		return pessoaRepository.findBynomeContaining(nome);
		
	}
	
	
	/* Cadastrando pessoa */
	@PostMapping
	public ResponseEntity<Pessoa> criarPessoa (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		/* retornando a informação salva no location requisição */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getId()).toUri();
		//response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pessoaSalva);
		
				
	}
	
	/* Atualizar Pessoa */
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@Valid @PathVariable Long codigo, @RequestBody Pessoa pessoa){
		
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
			
		return ResponseEntity.ok(pessoaSalva); 
	}
	
	/*Excluir Pessoa */
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long codigo) {
		pessoaRepository.deleteById(codigo);
	}
	

}
