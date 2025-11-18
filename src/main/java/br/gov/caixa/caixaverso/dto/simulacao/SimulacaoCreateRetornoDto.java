package br.gov.caixa.caixaverso.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoCreateRetornoDto {

    private ProdutoValidadoSimulacaoRetornoDto produtoValidado;

    private ResultadoSimulacaoRetornoDto resultadoSimulacao;

    private LocalDateTime dataSimulacao;
}
