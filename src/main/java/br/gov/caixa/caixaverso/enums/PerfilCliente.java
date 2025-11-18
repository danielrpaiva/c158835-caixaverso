package br.gov.caixa.caixaverso.enums;

import lombok.Getter;

@Getter
public enum PerfilCliente {
    CONSERVADOR("Conservador"),
    MODERADO("Moderado"),
    AGRESSIVO("Agressivo");

    private final String nome;

    PerfilCliente(String nome) {
        this.nome = nome;
    }

}
