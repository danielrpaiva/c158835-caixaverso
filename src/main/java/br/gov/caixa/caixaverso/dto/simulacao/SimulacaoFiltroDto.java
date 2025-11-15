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
    public Long clienteId;
    public Long produtoId;
    public String produtoNome;
    public LocalDateTime dataInicio;
    public LocalDateTime dataFim;
}
