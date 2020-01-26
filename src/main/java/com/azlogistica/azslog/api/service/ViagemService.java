package com.azlogistica.azslog.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.azlogistica.azslog.api.model.Viagem;
import com.azlogistica.azslog.api.repository.ViagemRepository;

@Service
public class ViagemService {

	@Autowired
	ViagemRepository viagemRepository;
	
	public Viagem atualizar(Long codigo, Viagem viagem) {
		
		Viagem viagemSalva = viagemRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(viagem, viagemSalva, "id");
		
		return viagemRepository.save(viagemSalva);
	
	}

}
