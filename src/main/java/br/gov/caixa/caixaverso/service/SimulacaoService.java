package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.simulacao.*;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.exception.TipoProdutoInvalidoException;
import br.gov.caixa.caixaverso.mapper.SimulacaoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.model.Simulacao;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.SimulacaoRepository;
import br.gov.caixa.caixaverso.util.Utilidades;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final ClienteRepository clienteRepository;
    private final MotorRecomendacaoService motorRecomendacaoService;
    private final SimulacaoMapper mapper;

    public SimulacaoService(
            SimulacaoRepository simulacaoRepository,
            SimulacaoMapper mapper,
            ClienteRepository clienteRepository,
            MotorRecomendacaoService motorRecomendacaoService
    ) {
        this.clienteRepository = clienteRepository;
        this.motorRecomendacaoService = motorRecomendacaoService;
        this.mapper = mapper;
        this.simulacaoRepository = simulacaoRepository;
    }

    @Transactional
    public List<SimulacaoItemRetornoDto> buscarSimulacoes(SimulacaoFiltroDto filtro) {
        List<Simulacao> simulacoes = simulacaoRepository.buscarComFiltro(filtro);
        return mapper.toSimulacaoRetornoList(simulacoes);
    }

    @Transactional
    public List<SimulacaoProdutoDiaRetornoDto> obterSimulacoesPorProdutoEDia(String data) {

        LocalDate dataConsulta;
        if (data != null) {
            dataConsulta = LocalDate.parse(data);
        } else {
            dataConsulta = LocalDate.now();
        }

        List<Simulacao> simulacoes = simulacaoRepository.buscarPorData(dataConsulta);

        return mapper.toSimulacaoProdutoDiaRetornoList(simulacoes, dataConsulta.toString());
    }

    @Transactional
    public SimulacaoCreateRetornoDto registrarSimulacao(SimulacaoCreateDto dto){

        TipoProduto tipoProduto = TipoProduto.obterTipoProduto(dto.getTipoProduto());

        if(tipoProduto == null){
            throw new TipoProdutoInvalidoException(dto.getTipoProduto());
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId());

        if (cliente == null) {
            throw new ClienteNaoEncontradoException(dto.getClienteId());
        }

        Produto produtoValidado = motorRecomendacaoService.buscarProdutoParaSimulacao(cliente, tipoProduto);

        Double valor = dto.getValor();
        Integer prazo = dto.getPrazoMeses();
        Double rentabilidadeAnual = produtoValidado.getRentabilidadeAnual();

        Simulacao novaSimulacao = new Simulacao();
        novaSimulacao.setCliente(cliente);
        novaSimulacao.setProduto(produtoValidado);
        novaSimulacao.setDataSimulacao(LocalDateTime.now());
        novaSimulacao.setValorInvestido(valor);
        novaSimulacao.setPrazoMeses(prazo);
        novaSimulacao.setValorFinal(
                Utilidades.calcularValorFinal(valor, prazo, rentabilidadeAnual)
        );
        novaSimulacao.setRentabilidadeEfetiva(
                Utilidades.calcularRentabilidadeEfetiva(valor, prazo, rentabilidadeAnual)
        );

        simulacaoRepository.persist(novaSimulacao);

        return mapper.toSimulacaoCreateRetornoDto(novaSimulacao);
    }
}
