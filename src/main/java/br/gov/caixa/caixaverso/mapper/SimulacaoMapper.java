package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.model.Simulacao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

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
}
