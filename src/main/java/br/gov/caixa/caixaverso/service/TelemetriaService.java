package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.exception.TelemetriaSemIntervaloException;
import br.gov.caixa.caixaverso.mapper.TelemetriaMapper;
import br.gov.caixa.caixaverso.model.Telemetria;
import br.gov.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TelemetriaService {

    private final TelemetriaRepository telemetriaRepository;
    private final TelemetriaMapper telemetriaMapper;

    public TelemetriaService(TelemetriaRepository telemetriaRepository, TelemetriaMapper telemetriaMapper) {
        this.telemetriaMapper = telemetriaMapper;
        this.telemetriaRepository = telemetriaRepository;
    }

    @Transactional
    public TelemetriaRetornoDto obterDadosTelemetria(String inicio, String fim) {
        if(inicio == null || fim == null) {
            throw new TelemetriaSemIntervaloException();
        }

        List<Telemetria> telemetrias = telemetriaRepository.buscarPorData(
                LocalDate.parse(inicio).atStartOfDay(),
                LocalDate.parse(fim).atStartOfDay()
        );

        return telemetriaMapper.toTelemetriaRetornoDto(telemetrias, inicio, fim);
    }

}
