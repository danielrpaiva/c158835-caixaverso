package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoFiltroDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoProdutoDiaRetornoDto;
import br.gov.caixa.caixaverso.service.SimulacaoService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
@Path("/api/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    private final SimulacaoService simulacaoService;

    public SimulacaoResource(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    // 2. Histórico de Simulações Realizadas
    @GET
    @Authenticated
    public Response buscarSimulacoes(
            @QueryParam("clienteId") Long clienteId,
            @QueryParam("produtoId") Long produtoId,
            @QueryParam("produtoNome") String produtoNome,
            @QueryParam("dataInicio") LocalDateTime dataInicio,
            @QueryParam("dataFim") LocalDateTime dataFim
    ) {

        SimulacaoFiltroDto filtro = new SimulacaoFiltroDto();
        filtro.setClienteId(clienteId);
        filtro.setProdutoId(produtoId);
        filtro.setProdutoNome(produtoNome);
        filtro.setDataInicio(dataInicio);
        filtro.setDataFim(dataFim);

        List<SimulacaoItemRetornoDto> dadosRetorno = simulacaoService.buscarSimulacoes(filtro);

        return Response.ok(dadosRetorno).build();
    }

    // 3. Valores Simulados por Produto e Dia
    @GET
    @Path("/por-produto-dia")
    @Authenticated
    public Response obterSimulacoesPorProdutoEDia(@QueryParam("data") String data) {
        List<SimulacaoProdutoDiaRetornoDto> resultado = simulacaoService.obterSimulacoesPorProdutoEDia(data);
        return Response.ok(resultado).build();
    }
}
