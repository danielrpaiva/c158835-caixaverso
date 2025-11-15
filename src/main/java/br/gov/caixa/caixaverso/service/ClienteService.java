package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteRetornoDto;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteService {

    private final KeycloakService keycloakService;
    private final ClienteRepository clienteRepository;

    public ClienteService(KeycloakService keycloakService, ClienteRepository clienteRepository) {
        this.keycloakService = keycloakService;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public RegistrarClienteRetornoDto registrarCliente(RegistrarClienteDto dto) {

        String keycloakId = keycloakService.criarUsuario(dto);

        Cliente cliente = new Cliente();
        cliente.setKeycloakId(keycloakId);
        cliente.setNome(dto.getNome());
        cliente.setPontuacaoRisco(0);
        cliente.setPerfil(PerfilCliente.CONSERVADOR);

        clienteRepository.persist(cliente);

        return new RegistrarClienteRetornoDto(cliente.getId());
    }

}
