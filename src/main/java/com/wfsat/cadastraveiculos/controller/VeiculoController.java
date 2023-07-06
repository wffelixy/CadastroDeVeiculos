package com.wfsat.cadastraveiculos.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wfsat.cadastraveiculos.model.Veiculo;
import com.wfsat.cadastraveiculos.repository.VeiculoRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoRepository veiculoRepository;

	@GetMapping
	public List<Veiculo> listar() {
		return veiculoRepository.findAll();
	}
	
	@GetMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> buscar(@PathVariable Long veiculoId) {

		return veiculoRepository.findById(veiculoId).map(veiculo -> ResponseEntity.ok(veiculo))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Veiculo cadastrar(@RequestBody Veiculo veiculo) {
		veiculo.setCreated(LocalDateTime.now());
		veiculo.setUpdated(LocalDateTime.now());
		
		return veiculoRepository.save(veiculo);
	}

	@PutMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> atualizar(@PathVariable Long veiculoId,
	        @RequestBody Veiculo veiculo) {

	    if (!veiculoRepository.existsById(veiculoId)) {
	        return ResponseEntity.notFound().build();
	    }

	    Veiculo veiculoExistente = veiculoRepository.findById(veiculoId).orElse(null);

	    veiculo.setCreated(veiculoExistente.getCreated());
	    veiculo.setUpdated(LocalDateTime.now());
	    
	    // Copiar os atributos do veiculoAtualizado para o veiculoExistente, ignorando o 'created'
	    BeanUtils.copyProperties(veiculo, veiculoExistente, "id", "created");
	    
	    

	    Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);

	    return ResponseEntity.ok(veiculoAtualizado);
	}


	@DeleteMapping("/{veiculoId}")
	public ResponseEntity<Void> remover(@PathVariable Long veiculoId) {
		if (!veiculoRepository.existsById(veiculoId)) {
			return ResponseEntity.notFound().build();
		}

		veiculoRepository.deleteById(veiculoId);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/filtro")
	public List<Veiculo> filtrarVeiculosPorMarcaEAno(@RequestParam(required = false) String marca,
	                                                @RequestParam(required = false) Integer ano) {
	    List<Veiculo> veiculos = veiculoRepository.findAll();

	    if (marca != null && ano != null) {
	        return veiculos.stream()
	                .filter(v -> v.getMarca().equalsIgnoreCase(marca) && v.getAno().equals(ano))
	                .collect(Collectors.toList());
	    } else if (marca != null) {
	        return veiculos.stream()
	                .filter(v -> v.getMarca().equalsIgnoreCase(marca))
	                .collect(Collectors.toList());
	    } else if (ano != null) {
	        return veiculos.stream()
	                .filter(v -> v.getAno().equals(ano))
	                .collect(Collectors.toList());
	    } else {
	        return veiculos;
	    }
	}
	

}