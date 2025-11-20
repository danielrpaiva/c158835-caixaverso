package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.exception.TelemetriaSemIntervaloValidoException;
import br.gov.caixa.caixaverso.mapper.TelemetriaMapper;
import br.gov.caixa.caixaverso.model.Telemetria;
import br.gov.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
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
            throw new TelemetriaSemIntervaloValidoException();
        }

        if (!inicio.matches("^\\d{4}-\\d{2}-\\d{2}$")
            || !fim.matches("^\\d{4}-\\d{2}-\\d{2}$")
            || LocalDate.parse(inicio).isAfter(LocalDate.parse(fim))) {
            throw new TelemetriaSemIntervaloValidoException();
        }

        List<Telemetria> telemetrias = telemetriaRepository.buscarPorData(
                LocalDate.parse(inicio).atStartOfDay(),
                LocalDate.parse(fim).atStartOfDay().plusDays(1).minusSeconds(1)
        );

        return telemetriaMapper.toTelemetriaRetornoDto(telemetrias, inicio, fim);
    }

}
