package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaPeriodoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaServicoDto;
import br.gov.caixa.caixaverso.service.TelemetriaService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TelemetriaResourceTest {

    private TelemetriaService telemetriaService;
    private TelemetriaResource telemetriaResource;

    @BeforeEach
    void setUp() {
        telemetriaService = Mockito.mock(TelemetriaService.class);
        telemetriaResource = new TelemetriaResource(telemetriaService);
    }

    private TelemetriaServicoDto setupTelemetriaServicoDto(String nome, Long chamadas, Double media) {
        TelemetriaServicoDto dto = new TelemetriaServicoDto();
        dto.setNome(nome);
        dto.setQuantidadeChamadas(chamadas);
        dto.setMediaTempoRespostaMs(media);
        return dto;
    }

    private TelemetriaPeriodoDto setupTelemetriaPeriodoDto(String inicio, String fim) {
        TelemetriaPeriodoDto dto = new TelemetriaPeriodoDto();
        dto.setInicio(inicio);
        dto.setFim(fim);
        return dto;
    }

    @Test
    void testObterDadosTelemetria() {
        String dataInicio = "2024-01-01";
        String dataFim = "2024-01-31";

        TelemetriaServicoDto servico1 = setupTelemetriaServicoDto("ServicoClientes", 100L, 50.5);
        TelemetriaServicoDto servico2 = setupTelemetriaServicoDto("ServicoProdutos", 200L, 15.2);
        List<TelemetriaServicoDto> listaServicos = Arrays.asList(servico1, servico2);

        TelemetriaPeriodoDto periodo = setupTelemetriaPeriodoDto(dataInicio, dataFim);

        TelemetriaRetornoDto retornoEsperado = new TelemetriaRetornoDto();
        retornoEsperado.setServicos(listaServicos);
        retornoEsperado.setPeriodo(periodo);

        when(telemetriaService.obterDadosTelemetria(dataInicio, dataFim)).thenReturn(retornoEsperado);

        try (Response response = telemetriaResource.obterDadosTelemetria(dataInicio, dataFim)) {
            TelemetriaRetornoDto responseDto = (TelemetriaRetornoDto) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(dataInicio, responseDto.getPeriodo().getInicio());
        }
    }
}