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
        tel1.setNomeServico("ServicoClientes");
        tel1.setMetodoHttp("GET");
        tel1.setStatusHttp(200);
        tel1.setTempoRespostaMs(150L);
        tel1.setDataChamada(data);

        Telemetria tel1Alt = new Telemetria();
        tel1Alt.setId(1L);
        tel1Alt.setNomeServico("ServicoClientes");
        tel1Alt.setMetodoHttp("GET");
        tel1Alt.setStatusHttp(200);
        tel1Alt.setTempoRespostaMs(150L);
        tel1Alt.setDataChamada(data);

        Telemetria tel2 = new Telemetria();
        tel2.setId(2L);
        tel2.setNomeServico("ServicoClientes");
        tel2.setMetodoHttp("GET");
        tel2.setStatusHttp(200);
        tel2.setTempoRespostaMs(150L);
        tel2.setDataChamada(data);

        assertEquals(tel1, tel1Alt);
        assertNotEquals(tel1, tel2);

        assertEquals(tel1.hashCode(), tel1Alt.hashCode());
        assertNotEquals(tel1.hashCode(), tel2.hashCode());
    }
}