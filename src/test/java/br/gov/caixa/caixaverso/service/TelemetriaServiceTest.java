package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.exception.TelemetriaSemIntervaloValidoException;
import br.gov.caixa.caixaverso.mapper.TelemetriaMapper;
import br.gov.caixa.caixaverso.model.Telemetria;
import br.gov.caixa.caixaverso.repository.TelemetriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TelemetriaServiceTest {

    private TelemetriaRepository telemetriaRepository;
    private TelemetriaMapper telemetriaMapper;
    private TelemetriaService telemetriaService;

    @BeforeEach
    void setUp(){
        telemetriaRepository = Mockito.mock(TelemetriaRepository.class);
        telemetriaMapper = Mockito.mock(TelemetriaMapper.class);
        telemetriaService = new TelemetriaService(telemetriaRepository, telemetriaMapper);
    }

    private TelemetriaRetornoDto setupTelemetriaRetornoDto() {
        TelemetriaRetornoDto dto = new TelemetriaRetornoDto();
        dto.setServicos(Collections.emptyList());
        return dto;
    }

    @Test
    void testObterDadosTelemetriaInicioNuloLancaExcecao() {
        assertThrows(TelemetriaSemIntervaloValidoException.class, () ->
                telemetriaService.obterDadosTelemetria(null, "2024-01-31")
        );
    }

    @Test
    void testObterDadosTelemetriaFimNuloLancaExcecao() {
        assertThrows(TelemetriaSemIntervaloValidoException.class, () ->
                telemetriaService.obterDadosTelemetria("2024-01-01", null)
        );
    }

    @Test
    void testObterDadosTelemetriaInicioPosteriorAoFimLancaExcecao() {
        assertThrows(TelemetriaSemIntervaloValidoException.class, () ->
                telemetriaService.obterDadosTelemetria("2024-02-01", "2024-01-01")
        );
    }

    @Test
    void testObterDadosTelemetriaSucesso() {
        String inicio = "2024-01-01";
        String fim = "2024-01-01";

        TelemetriaRetornoDto retornoEsperado = setupTelemetriaRetornoDto();

        List<Telemetria> telemetriasMocadas = Collections.singletonList(new Telemetria());

        when(telemetriaRepository.buscarPorData(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(telemetriasMocadas);
        when(telemetriaMapper.toTelemetriaRetornoDto(anyList(), anyString(), anyString()))
                .thenReturn(retornoEsperado);

        TelemetriaRetornoDto resultado = telemetriaService.obterDadosTelemetria(inicio, fim);

        assertEquals(retornoEsperado, resultado);

        ArgumentCaptor<LocalDateTime> inicioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> fimCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(telemetriaRepository).buscarPorData(inicioCaptor.capture(), fimCaptor.capture());

        assertEquals(LocalDate.parse(inicio).atStartOfDay(), inicioCaptor.getValue());

        LocalDateTime fimDoDiaEsperado = LocalDate.parse(fim).atStartOfDay().plusDays(1).minusSeconds(1);
        assertEquals(fimDoDiaEsperado, fimCaptor.getValue());

        verify(telemetriaMapper).toTelemetriaRetornoDto(telemetriasMocadas, inicio, fim);
    }
}