package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.PerfilCliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClienteTest {

    @Test
    void testEqualsEHashCode() {
        Cliente cli1 = new Cliente();
        cli1.setId(10L);
        cli1.setNome("Cliente Alpha");
        cli1.setKeycloakId("uuid-alpha-123");
        cli1.setPontuacao(25);
        cli1.setPerfil(PerfilCliente.CONSERVADOR);
        cli1.setSaldoRiscoBaixo(500.0);
        cli1.setSaldoRiscoMedio(500.0);
        cli1.setSaldoRiscoAlto(500.0);

        Cliente cli1Alt = new Cliente();
        cli1Alt.setId(10L);
        cli1Alt.setNome("Cliente Alpha");
        cli1Alt.setKeycloakId("uuid-alpha-123");
        cli1Alt.setPontuacao(25);
        cli1Alt.setPerfil(PerfilCliente.CONSERVADOR);
        cli1Alt.setSaldoRiscoBaixo(500.0);
        cli1Alt.setSaldoRiscoMedio(500.0);
        cli1Alt.setSaldoRiscoAlto(500.0);

        Cliente cli2 = new Cliente();
        cli2.setId(20L);
        cli2.setNome("Cliente Beta");
        cli2.setKeycloakId("uuid-beta-456");
        cli2.setPontuacao(90);
        cli2.setPerfil(PerfilCliente.AGRESSIVO);
        cli2.setSaldoRiscoBaixo(0.0);
        cli2.setSaldoRiscoMedio(0.0);
        cli2.setSaldoRiscoAlto(5000.0);


        assertEquals(cli1, cli1Alt);
        assertNotEquals(cli1, cli2);

        assertEquals(cli1.hashCode(), cli1Alt.hashCode());
        assertNotEquals(cli1.hashCode(), cli2.hashCode());
    }

}