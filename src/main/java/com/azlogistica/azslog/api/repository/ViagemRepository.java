package com.azlogistica.azslog.api.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.azlogistica.azslog.api.model.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long>{

	//busca viagem por motorista
	@Query(value = "SELECT * FROM viagem v INNER JOIN pessoa p ON v.pessoa_id = p.id WHERE p.nome LIKE %?1%", nativeQuery = true)
	List<Viagem> motorista(String nome);
	
	@Query(value= "SELECT * FROM viagem v INNER JOIN veiculo ve ON v.veiculo_id = ve.id where ve.placa = ?1", nativeQuery = true)
	List<Viagem> placa(String placa);
	
	List <Viagem> dataInicioIs(Date data);
}
