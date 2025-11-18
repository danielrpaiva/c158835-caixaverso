package br.gov.caixa.caixaverso.enums;

import lombok.Getter;

@Getter
public enum NivelRisco {
    BAIXO("Baixo"),
    MEDIO("Médio"),
    ALTO("Alto");

    private final String nome;

    NivelRisco(String nome) {
        this.nome = nome;
    }
}
