package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoCreateDto;
import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.caixaverso.mapper.InvestimentoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Investimento;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.InvestimentoRepository;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class InvestimentoService {

    private final MotorRecomendacaoService motorRecomendacaoService;
    private final InvestimentoRepository investimentoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final InvestimentoMapper mapper;

    public InvestimentoService(
            MotorRecomendacaoService motorRecomendacaoService,
            InvestimentoRepository investimentoRepository,
            ProdutoRepository produtoRepository,
            ClienteRepository clienteRepository,
            InvestimentoMapper mapper
    ) {
        this.motorRecomendacaoService = motorRecomendacaoService;
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
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

    @Transactional
    public InvestimentoRetornoDto registrarNovoInvestimento(InvestimentoCreateDto dto) {

        Produto produto = produtoRepository.findById(dto.getProdutoId());

        if(produto == null) {
            throw new ProdutoNaoEncontradoException(dto.getProdutoId());
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId());

        if(cliente == null) {
            throw new ClienteNaoEncontradoException(dto.getClienteId());
        }

        Investimento novoInvestimento = new Investimento();
        novoInvestimento.setCliente(cliente);
        novoInvestimento.setProduto(produto);
        novoInvestimento.setValor(dto.getValor());
        novoInvestimento.setRentabilidade(dto.getRentabilidade());
        novoInvestimento.setDataInvestimento(LocalDateTime.now());

        investimentoRepository.persist(novoInvestimento);

        switch (produto.getRisco()) {
            case BAIXO -> cliente.setSaldoRiscoBaixo(cliente.getSaldoRiscoBaixo() + dto.getValor());
            case MEDIO -> cliente.setSaldoRiscoMedio(cliente.getSaldoRiscoMedio() + dto.getValor());
            case ALTO -> cliente.setSaldoRiscoAlto(cliente.getSaldoRiscoAlto() + dto.getValor());
        }

        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);

        cliente.setPerfil(motorRecomendacaoService.definirPerfil(pontuacao));
        cliente.setPontuacao(pontuacao);

        return mapper.toInvestimentoRetornoDto(novoInvestimento);
    }
}
