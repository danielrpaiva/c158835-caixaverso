package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoProdutoDiaRetornoDto;
import br.gov.caixa.caixaverso.model.Simulacao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SimulacaoMapper {

    public List<SimulacaoItemRetornoDto> toSimulacaoRetornoList(List<Simulacao> simulacoes) {

        List<SimulacaoItemRetornoDto> listaDto = new ArrayList<SimulacaoItemRetornoDto>();

        for(Simulacao simulacao : simulacoes) {
            SimulacaoItemRetornoDto dto = new SimulacaoItemRetornoDto();
            dto.setId(simulacao.getId());
            dto.setClienteId(simulacao.getCliente().getId());
            dto.setProduto(simulacao.getProduto().getNome());
            dto.setValorInvestido(simulacao.getValorInvestido());
            dto.setValorFinal(simulacao.getValorFinal());
            dto.setPrazoMeses(simulacao.getPrazoMeses());
            dto.setDataSimulacao(simulacao.getDataSimulacao().toString());
            listaDto.add(dto);
        }

        return listaDto;
    }

    public List<SimulacaoProdutoDiaRetornoDto> toSimulacaoProdutoDiaRetornoList(
            List<Simulacao> simulacoes,
            String data
    ) {

        List<SimulacaoProdutoDiaRetornoDto> listaDto = new ArrayList<SimulacaoProdutoDiaRetornoDto>();

        Map<String, Long> contagemPorProduto = new HashMap<String, Long>();
        Map<String, Double> somaValorFinalPorProduto = new HashMap<String, Double>();

        for(Simulacao simulacao : simulacoes) {
            String produto = simulacao.getProduto().getNome();

            contagemPorProduto.put(
                    produto,
                    contagemPorProduto.getOrDefault(produto, 0L) + 1
            );
            somaValorFinalPorProduto.put(
                    produto,
                    somaValorFinalPorProduto.getOrDefault(produto, 0.0) + simulacao.getValorFinal()
            );
        }

        for(String produto : contagemPorProduto.keySet()) {
            Long quantidadeSimulacoes = contagemPorProduto.get(produto);
            Double valorFinalTotal = somaValorFinalPorProduto.get(produto);
            Double mediaValorFinal = valorFinalTotal / quantidadeSimulacoes;

            SimulacaoProdutoDiaRetornoDto dto = new SimulacaoProdutoDiaRetornoDto();
            dto.setProduto(produto);
            dto.setData(data);
            dto.setQuantidadeSimulacoes(quantidadeSimulacoes);
            dto.setMediaValorFinal(mediaValorFinal);

            listaDto.add(dto);
        }

        return listaDto;
    }
}
