package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.cliente.ClientePerfilRiscoRetornoDto;
import br.gov.caixa.caixaverso.service.ClienteService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

@RequestScoped
@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 5. Perfil de Risco
    @Operation(
            summary = "5. Perfil de Risco",
            description = "Endpoint 5 do enunciado"
    )
    @GET
    @Path("perfil-risco/{clienteId}")
    @Authenticated
    public Response obterPerfilRisco(@PathParam("clienteId") Long clienteId) {
        ClientePerfilRiscoRetornoDto perfilRisco = clienteService.obterPerfilRiscoCliente(clienteId);
        return Response.ok(perfilRisco).build();
    }
}
