package br.gov.caixa.caixaverso.enums;

import lombok.Getter;

@Getter
public enum TipoProduto {
    CDB("CDB"),
    LCI("LCI"),
    LCA("LCA"),
    TESOURO_DIRETO("Tesouro Direto"),
    FUNDO_DE_INVESTIMENTO("Fundo de Investimento"),
    ACAO("Ação"),
    CRIPTOMOEDA("Criptomoeda");

    private final String nome;

    TipoProduto(String nome) {
        this.nome = nome;
    }
}
