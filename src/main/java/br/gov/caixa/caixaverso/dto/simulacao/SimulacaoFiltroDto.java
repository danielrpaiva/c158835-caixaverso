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
public class SimulacaoFiltroDto {
    private Long clienteId;
    private Long produtoId;
    private String produtoNome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
