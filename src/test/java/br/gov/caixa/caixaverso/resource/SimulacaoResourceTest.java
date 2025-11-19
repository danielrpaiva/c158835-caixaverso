package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.simulacao.*;
import br.gov.caixa.caixaverso.service.SimulacaoService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimulacaoResourceTest {

    private SimulacaoService simulacaoService;
    private SimulacaoResource simulacaoResource;

    @BeforeEach
    void setUp() {
        simulacaoService = Mockito.mock(SimulacaoService.class);
        simulacaoResource = new SimulacaoResource(simulacaoService);
    }

    @Test
    void testBuscarSimulacoes() {
        Long clienteId = 1L;
        LocalDateTime dataInicio = LocalDateTime.now().minusDays(7);
        LocalDateTime dataFim = LocalDateTime.now();

        SimulacaoItemRetornoDto item1 = new SimulacaoItemRetornoDto(
                10L, 1L, "CDB", 1000.0, 1100.0, 12, "2024-01-01"
        );
        List<SimulacaoItemRetornoDto> listaEsperada = Arrays.asList(item1);

        when(simulacaoService.buscarSimulacoes(any(SimulacaoFiltroDto.class))).thenReturn(listaEsperada);

        try (Response response = simulacaoResource.buscarSimulacoes(
                clienteId,
                null,
                null,
                dataInicio,
                dataFim
        )) {
            ArgumentCaptor<SimulacaoFiltroDto> filtroCaptor = ArgumentCaptor.forClass(SimulacaoFiltroDto.class);
            verify(simulacaoService).buscarSimulacoes(filtroCaptor.capture());

            SimulacaoFiltroDto filtroPassado = filtroCaptor.getValue();
            List<SimulacaoItemRetornoDto> responseList = (List<SimulacaoItemRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(1, responseList.size());
            assertEquals(clienteId, filtroPassado.getClienteId());
            assertEquals(dataInicio, filtroPassado.getDataInicio());
        }
    }

    @Test
    void testRegistrarSimulacao() {
        SimulacaoCreateDto inputDto = new SimulacaoCreateDto();

        ProdutoValidadoSimulacaoRetornoDto produtoValidado = new ProdutoValidadoSimulacaoRetornoDto();
        ResultadoSimulacaoRetornoDto resultadoSimulacao = new ResultadoSimulacaoRetornoDto();
        LocalDateTime dataSimulacao = LocalDateTime.now();

        SimulacaoCreateRetornoDto retornoDtoEsperado = new SimulacaoCreateRetornoDto(
                produtoValidado,
                resultadoSimulacao,
                dataSimulacao
        );

        when(simulacaoService.registrarSimulacao(inputDto)).thenReturn(retornoDtoEsperado);

        try (Response response = simulacaoResource.registrarSimulacao(inputDto)) {
            SimulacaoCreateRetornoDto responseDto = (SimulacaoCreateRetornoDto) response.getEntity();

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertEquals(dataSimulacao, responseDto.getDataSimulacao());
        }
    }

    @Test
    void testObterSimulacoesPorProdutoEDia() {
        String data = "2024-05-20";

        SimulacaoProdutoDiaRetornoDto item1 = new SimulacaoProdutoDiaRetornoDto(
                "CDB", data, 5L, 1500.0
        );
        List<SimulacaoProdutoDiaRetornoDto> listaEsperada = Arrays.asList(item1);

        when(simulacaoService.obterSimulacoesPorProdutoEDia(data)).thenReturn(listaEsperada);

        try (Response response = simulacaoResource.obterSimulacoesPorProdutoEDia(data)) {
            List<SimulacaoProdutoDiaRetornoDto> responseList = (List<SimulacaoProdutoDiaRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(1, responseList.size());
            assertEquals(data, responseList.getFirst().getData());
            assertEquals(5L, responseList.getFirst().getQuantidadeSimulacoes());
        }
    }
}