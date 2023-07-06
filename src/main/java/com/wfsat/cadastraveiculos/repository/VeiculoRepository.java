package com.wfsat.cadastraveiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wfsat.cadastraveiculos.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	int countByVendidoFalse();

}
