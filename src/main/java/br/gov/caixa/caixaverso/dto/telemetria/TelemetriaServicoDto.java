package br.gov.caixa.caixaverso.dto.telemetria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelemetriaServicoDto {
    private String nome;

    private Long quantidadeChamadas;

    private Double mediaTempoRespostaMs;
}
