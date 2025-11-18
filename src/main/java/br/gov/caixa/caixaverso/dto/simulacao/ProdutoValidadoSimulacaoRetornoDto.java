package br.gov.caixa.caixaverso.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoValidadoSimulacaoRetornoDto {

    private Long id;

    private String nome;

    private String tipo;

    private Double rentabilidade;

    private String risco;
}
