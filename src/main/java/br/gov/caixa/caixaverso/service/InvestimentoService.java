package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.mapper.InvestimentoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Investimento;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.InvestimentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class InvestimentoService {

    private final InvestimentoRepository investimentoRepository;
    private final ClienteRepository clienteRepository;
    private final InvestimentoMapper mapper;

    public InvestimentoService(
            InvestimentoRepository investimentoRepository,
            ClienteRepository clienteRepository,
            InvestimentoMapper mapper
    ) {
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.investimentoRepository = investimentoRepository;
    }

    @Transactional
    public List<InvestimentoRetornoDto> obterInvestimentosPorCliente(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException(clienteId);
        }

        List<Investimento> investimentos = investimentoRepository.buscarPorCliente(cliente);

        return mapper.toInvestimentoRetornoDtoList(investimentos);
    }

}
