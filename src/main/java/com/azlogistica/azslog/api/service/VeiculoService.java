package com.azlogistica.azslog.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.azlogistica.azslog.api.model.Veiculo;
import com.azlogistica.azslog.api.repository.VeiculoRepository;

@Service
public class VeiculoService {
	
	@Autowired
	VeiculoRepository veiculoRepository;
	
	public Veiculo atualizar(Long codigo, Veiculo veiculo) {
		
		Veiculo veiculoSalvo = veiculoRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(veiculo, veiculoSalvo, "id");
		
		return veiculoRepository.save(veiculoSalvo);
	
	}

}
