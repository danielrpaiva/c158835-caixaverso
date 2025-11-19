package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteRetornoDto;
import br.gov.caixa.caixaverso.service.ClienteService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AutenticacaoResourceTest {

    private ClienteService clienteService;
    private AutenticacaoResource autenticacaoResource;

    @BeforeEach
    void setUp(){
        clienteService = Mockito.mock(ClienteService.class);
        autenticacaoResource = new AutenticacaoResource(clienteService);
    }

    private RegistrarClienteDto setupRegistrarClienteDto() {
        RegistrarClienteDto dto = new RegistrarClienteDto();
        dto.setNome("Daniel");
        dto.setSobrenome("Paiva");
        dto.setEmail("daniel.r.paiva@caixa.gov.br");
        dto.setSenha("SenhaSegura123");
        return dto;
    }

    @Test
    void testRegistrar() {
        RegistrarClienteDto inputDto = setupRegistrarClienteDto();
        RegistrarClienteRetornoDto retornoDtoEsperado = new RegistrarClienteRetornoDto(100L);

        when(clienteService.registrarCliente(inputDto)).thenReturn(retornoDtoEsperado);

        try (Response response = autenticacaoResource.registrar(inputDto)) {
            RegistrarClienteRetornoDto responseDto = (RegistrarClienteRetornoDto) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(100L, responseDto.getId());
        }
    }
}