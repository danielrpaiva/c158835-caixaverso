package br.gov.caixa.caixaverso.util;

import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;

public class Utilidades {

    public static String descricaoPerfilCliente(PerfilCliente perfilCliente) {
        switch (perfilCliente) {
            case CONSERVADOR:
                return "Perfil focado mais em segurança do que rentabilidade.";
            case MODERADO:
                return "Perfil equilibrado entre segurança e rentabilidade.";
            case AGGRESIVO:
                return "Perfil focado mais em rentabilidade do que segurança.";
            default:
                return "Perfil de Cliente Desconhecido";
        }
    }
}
