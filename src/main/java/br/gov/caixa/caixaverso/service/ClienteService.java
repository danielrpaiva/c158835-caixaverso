package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.cliente.ClientePerfilRiscoRetornoDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteRetornoDto;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.util.Utilidades;
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
        cliente.setPontuacao(0);
        cliente.setPerfil(PerfilCliente.CONSERVADOR);

        clienteRepository.persist(cliente);

        return new RegistrarClienteRetornoDto(cliente.getId());
    }

    @Transactional
    public ClientePerfilRiscoRetornoDto obterPerfilRiscoCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException(clienteId);
        }

        return new ClientePerfilRiscoRetornoDto(
                cliente.getId(),
                cliente.getPerfil().getNome(),
                cliente.getPontuacao(),
                Utilidades.descricaoPerfilCliente(cliente.getPerfil())
        );
    }
}
