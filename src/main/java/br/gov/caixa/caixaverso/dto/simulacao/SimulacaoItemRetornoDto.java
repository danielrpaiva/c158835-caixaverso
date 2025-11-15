package br.gov.caixa.caixaverso.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoItemRetornoDto {
    private Long id;

    private Long clienteId;

    private String produto;

    private Double valorInvestido;

    private Double valorFinal;

    private Integer prazoMeses;

    private String dataSimulacao;
}
