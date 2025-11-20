package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaPeriodoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaServicoDto;
import br.gov.caixa.caixaverso.model.Telemetria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TelemetriaMapperTest {

    private TelemetriaMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new TelemetriaMapper();
    }

    private Telemetria setupTelemetria(String nomeServico, Long tempoRespostaMs) {
        Telemetria telemetria = new Telemetria();
        telemetria.setId(1L);
        telemetria.setNomeServico(nomeServico);
        telemetria.setMetodoHttp("GET");
        telemetria.setStatusHttp(200);
        telemetria.setTempoRespostaMs(tempoRespostaMs);
        telemetria.setDataChamada(LocalDateTime.now());
        return telemetria;
    }

    @Test
    void testToTelemetriaRetornoDtoComAgrupamentoEcalculoDeMedia() {
        String inicio = "2024-01-01";
        String fim = "2024-01-31";

        // Serviço A: 2 chamadas, tempos 100ms e 200ms. Média = 150ms
        Telemetria telA1 = setupTelemetria("ServicoClientes", 100L);
        Telemetria telA2 = setupTelemetria("ServicoClientes", 200L);

        // Serviço B: 1 chamada, tempo 500ms. Média = 500ms
        Telemetria telB1 = setupTelemetria("ServicoProdutos", 500L);

        List<Telemetria> listaEntrada = Arrays.asList(telA1, telA2, telB1);

        TelemetriaRetornoDto dto = mapper.toTelemetriaRetornoDto(listaEntrada, inicio, fim);

        assertNotNull(dto);
        assertEquals(inicio, dto.getPeriodo().getInicio());
        assertEquals(fim, dto.getPeriodo().getFim());
        assertEquals(2, dto.getServicos().size());

        TelemetriaServicoDto servicoA = dto.getServicos().stream()
                .filter(s -> s.getNome().equals("ServicoClientes"))
                .findFirst()
                .orElse(null);

        assertNotNull(servicoA);
        assertEquals(2L, servicoA.getQuantidadeChamadas());
        assertEquals(150.0, servicoA.getMediaTempoRespostaMs()); // (100 + 200) / 2 = 150.0

        TelemetriaServicoDto servicoB = dto.getServicos().stream()
                .filter(s -> s.getNome().equals("ServicoProdutos"))
                .findFirst()
                .orElse(null);

        assertNotNull(servicoB);
        assertEquals(1L, servicoB.getQuantidadeChamadas());
        assertEquals(500.0, servicoB.getMediaTempoRespostaMs()); // 500 / 1 = 500.0
    }

    @Test
    void testToTelemetriaRetornoDtoListaVazia() {
        String inicio = "2024-01-01";
        String fim = "2024-01-01";
        List<Telemetria> listaEntrada = Collections.emptyList();

        TelemetriaRetornoDto dto = mapper.toTelemetriaRetornoDto(listaEntrada, inicio, fim);

        assertNotNull(dto);
        assertTrue(dto.getServicos().isEmpty());
        assertEquals(inicio, dto.getPeriodo().getInicio());
    }
}