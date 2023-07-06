package com.example.felix.teste;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.wfsat.cadastraveiculos.controller.VeiculoController;
import com.wfsat.cadastraveiculos.model.Veiculo;
import com.wfsat.cadastraveiculos.repository.VeiculoRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {VeiculoControllerTest.class})
public class VeiculoControllerTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoController veiculoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAtualizarParcial() {
        Long veiculoId = 1L;
        Veiculo veiculoExistente = new Veiculo();
        veiculoExistente.setId(veiculoId);
        veiculoExistente.setMarca("Ford");
        veiculoExistente.setAno(2020);
        veiculoExistente.setDescricao("Descrição do veículo");
        veiculoExistente.setVendido(false);
        veiculoExistente.setCreated(LocalDateTime.now());
        veiculoExistente.setUpdated(LocalDateTime.now());

        Map<String, Object> camposAtualizados = new HashMap<>();
        camposAtualizados.put("marca", "Chevrolet");
        camposAtualizados.put("ano", 2022);

        when(veiculoRepository.findById(veiculoId)).thenReturn(java.util.Optional.of(veiculoExistente));
        when(veiculoRepository.save(veiculoExistente)).thenReturn(veiculoExistente);

        ResponseEntity<Veiculo> response = veiculoController.atualizarParcial(veiculoId, camposAtualizados);

        verify(veiculoRepository, times(1)).findById(veiculoId);
        verify(veiculoRepository, times(1)).save(veiculoExistente);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Veiculo veiculoAtualizado = response.getBody();
        Assertions.assertNotNull(veiculoAtualizado);
        Assertions.assertEquals("Chevrolet", veiculoAtualizado.getMarca());
        Assertions.assertEquals(2022, veiculoAtualizado.getAno());
    }
}
