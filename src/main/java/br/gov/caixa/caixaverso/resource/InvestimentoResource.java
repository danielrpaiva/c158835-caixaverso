package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.service.InvestimentoService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/api/investimentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestimentoResource {

    private final InvestimentoService investimentoService;

    public InvestimentoResource(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    // TODO: Vai precisar de um endpoint POST para armazenar os investimentos

    // 7.Histórico de Investimentos
    @GET
    @Path("/{clienteId}")
    @Authenticated
    public Response obterInvestimentosPorCliente(@PathParam("clienteId") Long clienteId) {
        return Response.ok(investimentoService.obterInvestimentosPorCliente(clienteId)).build();
    }

}
