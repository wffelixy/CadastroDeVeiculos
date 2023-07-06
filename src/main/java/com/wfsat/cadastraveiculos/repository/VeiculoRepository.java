package com.wfsat.cadastraveiculos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wfsat.cadastraveiculos.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	int countByVendidoFalse();

	List<Veiculo> findByCreatedBetween(LocalDateTime dataInicioSemana, LocalDateTime dataAtual);

}
