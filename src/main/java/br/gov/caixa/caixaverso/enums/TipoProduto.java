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

    public static TipoProduto obterTipoProduto(String texto) {

        for (TipoProduto produto : TipoProduto.values()) {
            if (produto.nome.equalsIgnoreCase(texto)) {
                return produto;
            }
        }

        return null;
    }

    public static String tipoProdutosDisponiveis(){

        StringBuilder tipos = new StringBuilder();

        String currTipo;
        for(TipoProduto tipoProduto : TipoProduto.values()){
            currTipo = tipoProduto.getNome() + "\n";
            tipos.append(currTipo);
        }

        return tipos.toString();
    }
}
