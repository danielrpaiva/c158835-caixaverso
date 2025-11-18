package br.gov.caixa.caixaverso.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoSimulacaoRetornoDto {

    private Double valorFinal;

    private Double rentabilidadeEfetiva;

    private Integer prazoMeses;
}
