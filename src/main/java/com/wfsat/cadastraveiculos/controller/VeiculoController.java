package com.wfsat.cadastraveiculos.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<Veiculo> cadastrar(@RequestBody Veiculo veiculo) {
		veiculo.setCreated(LocalDateTime.now());
		veiculo.setUpdated(LocalDateTime.now());

		try {
			validaMarca(veiculo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}		
		
		Veiculo novoVeiculo = veiculoRepository.save(veiculo);

		return ResponseEntity.ok(novoVeiculo);
	}

	@PutMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> atualizar(@PathVariable Long veiculoId, @RequestBody Veiculo veiculo) {

		if (!veiculoRepository.existsById(veiculoId)) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			validaMarca(veiculo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}	

		Veiculo veiculoExistente = veiculoRepository.findById(veiculoId).orElse(null);

		veiculo.setCreated(veiculoExistente.getCreated());
		veiculo.setUpdated(LocalDateTime.now());

		// Copiar os atributos do veiculoAtualizado para o veiculoExistente
		BeanUtils.copyProperties(veiculo, veiculoExistente, "id", "created");

		validaMarca(veiculoExistente);

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

	@PatchMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> atualizarParcial(@PathVariable Long veiculoId,
			@RequestBody Map<String, Object> camposAtualizados) {

		Veiculo veiculoExistente = veiculoRepository.findById(veiculoId).orElse(null);
		if (veiculoExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		// Atualiza apenas os campos fornecidos no corpo da requisição
		camposAtualizados.forEach((campo, valor) -> {
			switch (campo) {
			case "marca":
				veiculoExistente.setMarca((String) valor);
				break;
			case "ano":
				veiculoExistente.setAno((Integer) valor);
				break;
			case "descricao":
				veiculoExistente.setDescricao((String) valor);
				break;
			case "vendido":
				veiculoExistente.setVendido((Boolean) valor);
				break;
			default:
				// Ignora os campos que não pertence ao veículo
				break;
			}
		});

		veiculoExistente.setUpdated(LocalDateTime.now());
		
		try {
			validaMarca(veiculoExistente);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);

		return ResponseEntity.ok(veiculoAtualizado);
	}

	@GetMapping("/filtro")
	public List<Veiculo> filtrarVeiculosPorMarcaEAno(@RequestParam(required = false) String marca,
			@RequestParam(required = false) Integer ano) {
		List<Veiculo> veiculos = veiculoRepository.findAll();

		if (marca != null && ano != null) {
			return veiculos.stream().filter(v -> v.getMarca().equalsIgnoreCase(marca) && v.getAno().equals(ano))
					.collect(Collectors.toList());
		} else if (marca != null) {
			return veiculos.stream().filter(v -> v.getMarca().equalsIgnoreCase(marca)).collect(Collectors.toList());
		} else if (ano != null) {
			return veiculos.stream().filter(v -> v.getAno().equals(ano)).collect(Collectors.toList());
		} else {
			return veiculos;
		}
	}

	@GetMapping("/nao-vendidos/quantidade")
	public int contarNaoVendidos() {
		return veiculoRepository.countByVendidoFalse();
	}

	@GetMapping("/distribuicao-decada")
	public Map<String, Integer> distribuicaoPorDecada() {
		List<Veiculo> veiculos = veiculoRepository.findAll();

		Map<String, Integer> distribuicaoDecada = new HashMap<>();

		for (Veiculo veiculo : veiculos) {
			int decada = veiculo.getAno() / 10 * 10;
			String chave = "Década " + decada;
			distribuicaoDecada.put(chave, distribuicaoDecada.getOrDefault(chave, 0) + 1);
		}

		return distribuicaoDecada;
	}

	@GetMapping("/distribuicao-fabricante")
	public Map<String, Integer> distribuicaoPorFabricante() {
		List<Veiculo> veiculos = veiculoRepository.findAll();

		Map<String, Integer> distribuicaoFabricante = new HashMap<>();

		for (Veiculo veiculo : veiculos) {
			String fabricante = veiculo.getMarca();
			distribuicaoFabricante.put(fabricante, distribuicaoFabricante.getOrDefault(fabricante, 0) + 1);
		}

		return distribuicaoFabricante;
	}

	@GetMapping("/registrados-ultima-semana")
	public List<Veiculo> listarRegistradosUltimaSemana() {
		LocalDateTime dataAtual = LocalDateTime.now();
		LocalDateTime dataInicioSemana = dataAtual.minusWeeks(1);

		return veiculoRepository.findByCreatedBetween(dataInicioSemana, dataAtual);
	}

	public void validaMarca(Veiculo veiculo) {
		// Verificar se a marca fornecida está na lista de marcas válidas
		List<String> marcasValidas = Arrays.asList("Ford", "Honda", "Chevrolet", "Volkswagen", "Toyota", "Fiat");
																					
		String marcaFornecida = veiculo.getMarca();

		if (!marcasValidas.contains(marcaFornecida)) {
			throw new IllegalArgumentException("Marca fornecida inválida");
		}
	}
	
}