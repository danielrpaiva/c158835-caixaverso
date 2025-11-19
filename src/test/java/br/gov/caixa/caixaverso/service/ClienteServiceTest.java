package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.cliente.ClientePerfilRiscoRetornoDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteRetornoDto;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteService clienteService;
    private KeycloakService keycloakService;
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp(){
        clienteRepository = Mockito.mock(ClienteRepository.class);
        keycloakService = Mockito.mock(KeycloakService.class);
        clienteService = new ClienteService(keycloakService, clienteRepository);
    }

    @Test
    void testRegistrarCliente(){

        RegistrarClienteDto registroDto = new RegistrarClienteDto();

        when(keycloakService.criarUsuario(any(RegistrarClienteDto.class))).thenReturn("uuidkd-kc-123");

        doAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.setId(1L);
            return null;
        }).when(clienteRepository).persist(any(Cliente.class));

        RegistrarClienteRetornoDto retornoDto = clienteService.registrarCliente(registroDto);

        assertInstanceOf(Long.class, retornoDto.getId());
    }

    @Test
    void testObterPerfilRiscoClienteSucesso(){
        Cliente cliente = new Cliente();
        cliente.setPontuacao(50);
        cliente.setPerfil(PerfilCliente.MODERADO);
        cliente.setNome("Daniel");
        cliente.setId(5L);
        cliente.setKeycloakId("uuidkd-kc-123");
        cliente.setSaldoRiscoAlto(10000.0);
        cliente.setSaldoRiscoMedio(50000.0);
        cliente.setSaldoRiscoAlto(0.0);

        when(clienteRepository.findById(anyLong())).thenReturn(cliente);

        ClientePerfilRiscoRetornoDto retornoDto = clienteService.obterPerfilRiscoCliente(5L);
        assertEquals(5L, retornoDto.getClienteId());
        assertEquals(PerfilCliente.MODERADO.getNome(), retornoDto.getPerfil());
        assertEquals(50, retornoDto.getPontuacao());
    }

    @Test
    void testObterPerfilRiscoClienteClienteNaoEncontrado(){
        when(clienteRepository.findById(anyLong())).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.obterPerfilRiscoCliente(123L));
    }
}
