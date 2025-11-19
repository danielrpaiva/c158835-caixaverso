package br.gov.caixa.caixaverso.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TelemetriaTest {

    @Test
    void testEqualsEHashCode() {
        LocalDateTime data = LocalDateTime.now();

        Telemetria tel1 = new Telemetria();
        tel1.setId(1L);
        tel1.nomeServico = "ServicoClientes";
        tel1.metodoHttp = "GET";
        tel1.statusHttp = 200;
        tel1.tempoRespostaMs = 150L;
        tel1.dataChamada = data;

        Telemetria tel1Alt = new Telemetria();
        tel1Alt.setId(1L);
        tel1Alt.nomeServico = "ServicoClientes";
        tel1Alt.metodoHttp = "GET";
        tel1Alt.statusHttp = 200;
        tel1Alt.tempoRespostaMs = 150L;
        tel1Alt.dataChamada = data;

        Telemetria tel2 = new Telemetria();
        tel2.setId(2L);
        tel2.nomeServico = "ServicoClientes";
        tel2.metodoHttp = "GET";
        tel2.statusHttp = 200;
        tel2.tempoRespostaMs = 150L;
        tel2.dataChamada = data;

        assertEquals(tel1, tel1Alt);
        assertNotEquals(tel1, tel2);

        assertEquals(tel1.hashCode(), tel1Alt.hashCode());
        assertNotEquals(tel1.hashCode(), tel2.hashCode());
    }
}