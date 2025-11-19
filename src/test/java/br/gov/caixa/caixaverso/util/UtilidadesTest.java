package br.gov.caixa.caixaverso.util;

import br.gov.caixa.caixaverso.enums.PerfilCliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilidadesTest {

    @Test
    void testDescricaoPerfilClienteConservador() {
        String descricao = Utilidades.descricaoPerfilCliente(PerfilCliente.CONSERVADOR);
        assertEquals("Perfil focado mais em segurança do que rentabilidade.", descricao);
    }

    @Test
    void testDescricaoPerfilClienteModerado() {
        String descricao = Utilidades.descricaoPerfilCliente(PerfilCliente.MODERADO);
        assertEquals("Perfil equilibrado entre segurança e rentabilidade.", descricao);
    }

    @Test
    void testDescricaoPerfilClienteAgressivo() {
        String descricao = Utilidades.descricaoPerfilCliente(PerfilCliente.AGRESSIVO);
        assertEquals("Perfil focado mais em rentabilidade do que segurança.", descricao);
    }

    @Test
    void testArredondar() {
        double valorEntrada = 12.34567;
        double valorEsperado = 12.35;
        double valorArredondado = Utilidades.arredondar(valorEntrada, 2);
        assertEquals(valorEsperado, valorArredondado, 0.001);
    }

    @Test
    void testCalcularValorFinal() {
        Double capital = 1000.0;
        Integer prazoMeses = 12;
        Double rentabilidadeAnual = 0.12;

        Double valorFinal = Utilidades.calcularValorFinal(capital, prazoMeses, rentabilidadeAnual);
        // Valor final esperado para (1000 * (1 + ( (1.12)^(1/12) - 1 ) )^12) arredondado para 2 casas
        assertEquals(1120.00, valorFinal, 0.01);
    }


    @Test
    void testCalcularRentabilidadeEfetiva() {
        Double capital = 1000.0;
        Integer prazoMeses = 12;
        Double rentabilidadeAnual = 0.12;

        Double rentabilidadeEfetiva = Utilidades.calcularRentabilidadeEfetiva(capital, prazoMeses, rentabilidadeAnual);
        // (1120.00 - 1000) / 1000 = 0.1200
        assertEquals(0.1200, rentabilidadeEfetiva, 0.0001);
    }
}