package br.gov.caixa.caixaverso.dto.telemetria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelemetriaRetornoDto {

    private List<TelemetriaServicoDto> servicos;

    private TelemetriaPeriodoDto periodo;

}
