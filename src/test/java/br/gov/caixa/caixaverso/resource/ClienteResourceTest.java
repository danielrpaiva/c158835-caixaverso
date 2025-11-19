package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.cliente.ClientePerfilRiscoRetornoDto;
import br.gov.caixa.caixaverso.service.ClienteService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClienteResourceTest {

    private ClienteService clienteService;
    private ClienteResource clienteResource;

    @BeforeEach
    void setUp(){
        clienteService = Mockito.mock(ClienteService.class);
        clienteResource = new ClienteResource(clienteService);
    }

    @Test
    void testObterPerfilRisco(){
        ClientePerfilRiscoRetornoDto perfilRiscoRetornoDto = new ClientePerfilRiscoRetornoDto();
        perfilRiscoRetornoDto.setClienteId(1L);
        perfilRiscoRetornoDto.setPerfil("Moderado");
        perfilRiscoRetornoDto.setDescricao("Equilibrado entre risco e rentabilidade");
        perfilRiscoRetornoDto.setPontuacao(50);

        when(clienteService.obterPerfilRiscoCliente(1L)).thenReturn(perfilRiscoRetornoDto);

        try(Response response = clienteResource.obterPerfilRisco(1L)){

            ClientePerfilRiscoRetornoDto responseDto = (ClientePerfilRiscoRetornoDto) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("Moderado", responseDto.getPerfil());
            assertEquals("Equilibrado entre risco e rentabilidade", responseDto.getDescricao());
            assertEquals(50, responseDto.getPontuacao());
        }
    }
}
