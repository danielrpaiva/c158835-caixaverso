package br.gov.caixa.caixaverso.util;

import br.gov.caixa.caixaverso.enums.PerfilCliente;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilidades {

    public static String descricaoPerfilCliente(PerfilCliente perfilCliente) {
        switch (perfilCliente) {
            case CONSERVADOR:
                return "Perfil focado mais em segurança do que rentabilidade.";
            case MODERADO:
                return "Perfil equilibrado entre segurança e rentabilidade.";
            case AGRESSIVO:
                return "Perfil focado mais em rentabilidade do que segurança.";
            default:
                return "Perfil de Cliente Desconhecido";
        }
    }

    public static double arredondar(double valor, int casas) {
        return BigDecimal
                .valueOf(valor)
                .setScale(casas, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static Double calcularValorFinal(
            Double capital,
            Integer prazoMeses,
            Double rentabilidadeAnual
    ){

        double rentabilidadeMensal = Math.pow(1 + rentabilidadeAnual, 1D / 12D) - 1;

        double montante = capital * Math.pow((1 + rentabilidadeMensal), prazoMeses);

        return arredondar(montante, 2);
    }

    public static Double calcularRentabilidadeEfetiva(
            Double capital,
            Integer prazoMeses,
            Double rentabilidadeAnual
    ){
        Double montante = calcularValorFinal(capital, prazoMeses, rentabilidadeAnual);

        return arredondar((montante - capital) / capital, 4);
    }
}
