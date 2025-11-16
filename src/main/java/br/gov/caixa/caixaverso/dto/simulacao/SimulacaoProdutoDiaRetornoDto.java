package br.gov.caixa.caixaverso.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoProdutoDiaRetornoDto {

    private String produto;
    private String data;
    private Long quantidadeSimulacoes;
    private Double mediaValorFinal;
}
