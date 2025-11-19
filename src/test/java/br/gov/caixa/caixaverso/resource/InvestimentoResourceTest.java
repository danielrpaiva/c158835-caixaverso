package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoCreateDto;
import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.service.InvestimentoService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class InvestimentoResourceTest {

    private InvestimentoService investimentoService;
    private InvestimentoResource investimentoResource;

    @BeforeEach
    void setUp(){
        investimentoService = Mockito.mock(InvestimentoService.class);
        investimentoResource = new InvestimentoResource(investimentoService);
    }

    @Test
    void testObterInvestimentosPorCliente(){
        Long clienteId = 5L;

        InvestimentoRetornoDto inv1 = new InvestimentoRetornoDto(10L, "CDB", 1000.0, 0.05, "2024-01-01");
        InvestimentoRetornoDto inv2 = new InvestimentoRetornoDto(11L, "LCI", 2000.0, 0.06, "2024-02-01");
        List<InvestimentoRetornoDto> listaEsperada = Arrays.asList(inv1, inv2);

        when(investimentoService.obterInvestimentosPorCliente(clienteId)).thenReturn(listaEsperada);

        try(Response response = investimentoResource.obterInvestimentosPorCliente(clienteId)){

            List<InvestimentoRetornoDto> responseList = (List<InvestimentoRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertNotNull(responseList);
            assertEquals(2, responseList.size());
            assertEquals(10L, responseList.getFirst().getId());
        }
    }

    @Test
    void testAdicionarInvestimento() {
        InvestimentoCreateDto inputDto = new InvestimentoCreateDto();

        InvestimentoRetornoDto retornoDtoEsperado = new InvestimentoRetornoDto(
                20L,
                "Tesouro Direto",
                500.0,
                0.07,
                "2025-01-01"
        );

        when(investimentoService.registrarNovoInvestimento(inputDto)).thenReturn(retornoDtoEsperado);

        try (Response response = investimentoResource.adicionarInvestimento(inputDto)) {
            InvestimentoRetornoDto responseDto = (InvestimentoRetornoDto) response.getEntity();

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertEquals(20L, responseDto.getId());
            assertEquals("Tesouro Direto", responseDto.getTipo());
        }
    }
}