package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.model.Investimento;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InvestimentoMapper {

    public List<InvestimentoRetornoDto> toInvestimentoRetornoDtoList(List<Investimento> investimentos) {

        List<InvestimentoRetornoDto> investimentoRetornoDtoList = new ArrayList<InvestimentoRetornoDto>();

        for(Investimento investimento : investimentos) {
            investimentoRetornoDtoList.add(toInvestimentoRetornoDto(investimento));
        }

        return investimentoRetornoDtoList;
    }

    public InvestimentoRetornoDto toInvestimentoRetornoDto(Investimento investimento) {
        InvestimentoRetornoDto dto = new InvestimentoRetornoDto();
        dto.setId(investimento.getId());
        dto.setTipo(investimento.getProduto().getTipo().getNome());
        dto.setValor(investimento.getValor());
        dto.setRentabilidade(investimento.getRentabilidade());
        dto.setData(investimento.getDataInvestimento().toString());
        return dto;
    }
}
