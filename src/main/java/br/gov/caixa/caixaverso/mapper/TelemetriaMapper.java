package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaPeriodoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaServicoDto;
import br.gov.caixa.caixaverso.model.Telemetria;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TelemetriaMapper {

    public TelemetriaRetornoDto toTelemetriaRetornoDto(List<Telemetria> telemetrias, String inicio, String fim) {

        TelemetriaRetornoDto retDto = new TelemetriaRetornoDto();
        List<TelemetriaServicoDto> servicosDto = new ArrayList<TelemetriaServicoDto>();
        TelemetriaPeriodoDto periodoDto = new TelemetriaPeriodoDto(inicio, fim);

        Map<String, Long> contagemPorServico = new HashMap<String, Long>();
        Map<String, Double> somaTempoPorServico = new HashMap<String, Double>();

        for (Telemetria telemetria : telemetrias) {
            String servico = telemetria.getNomeServico();
            contagemPorServico.put(
                    servico,
                    contagemPorServico.getOrDefault(servico, 0L) + 1
            );
            somaTempoPorServico.put(
                    servico,
                    somaTempoPorServico.getOrDefault(servico, 0.0) + telemetria.getTempoRespostaMs()
            );
        }

        for (String servico : contagemPorServico.keySet()) {
            Long contagem = contagemPorServico.get(servico);
            Double somaTempo = somaTempoPorServico.get(servico);
            Double tempoMedio = somaTempo / contagem;

            TelemetriaServicoDto servicoDto = new TelemetriaServicoDto();
            servicoDto.setNome(servico);
            servicoDto.setQuantidadeChamadas(contagem);
            servicoDto.setMediaTempoRespostaMs(tempoMedio);
            servicosDto.add(servicoDto);
        }

        retDto.setServicos(servicosDto);
        retDto.setPeriodo(periodoDto);

        return retDto;
    }
}
