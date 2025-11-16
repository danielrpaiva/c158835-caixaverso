package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import br.gov.caixa.caixaverso.service.ClienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutenticacaoResource {

    private final ClienteService clienteService;

    public AutenticacaoResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @POST
    @Path("/registro")
    public Response registrar(@Valid RegistrarClienteDto dto) {
        var user = clienteService.registrarCliente(dto);
        return Response.ok(user).build();
    }
}
