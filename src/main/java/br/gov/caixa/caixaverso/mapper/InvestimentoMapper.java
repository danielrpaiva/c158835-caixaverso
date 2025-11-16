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
            InvestimentoRetornoDto dto = new InvestimentoRetornoDto();
            dto.setId(investimento.getId());
            dto.setTipo(investimento.getProduto().getTipo().getNome());
            dto.setValor(investimento.getValor());
            dto.setRentabilidade(investimento.getRentabilidadeEfetiva());
            dto.setData(investimento.getDataInvestimento().toString());
            investimentoRetornoDtoList.add(dto);
        }

        return investimentoRetornoDtoList;
    }
}
