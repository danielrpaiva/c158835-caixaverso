package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoFiltroDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.mapper.SimulacaoMapper;
import br.gov.caixa.caixaverso.model.Simulacao;
import br.gov.caixa.caixaverso.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final SimulacaoMapper mapper;

    public SimulacaoService(SimulacaoRepository simulacaoRepository, SimulacaoMapper mapper) {
        this.mapper = mapper;
        this.simulacaoRepository = simulacaoRepository;
    }

    @Transactional
    public List<SimulacaoItemRetornoDto> buscarSimulacoes(SimulacaoFiltroDto filtro) {
        List<Simulacao> simulacoes = simulacaoRepository.buscarComFiltro(filtro);
        return mapper.toSimulacaoRetornoList(simulacoes);
    }
}
