package com.azlogistica.azslog.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azlogistica.azslog.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	Pessoa findByCpf(String cpf);
	
	
	List<Pessoa> findBynomeContaining(String nome);
}
