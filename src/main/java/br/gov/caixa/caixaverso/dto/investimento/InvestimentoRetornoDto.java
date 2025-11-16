package br.gov.caixa.caixaverso.dto.investimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoRetornoDto {
    private Long id;
    private String tipo;
    private Double valor;
    private Double rentabilidade;
    private String data;
}
