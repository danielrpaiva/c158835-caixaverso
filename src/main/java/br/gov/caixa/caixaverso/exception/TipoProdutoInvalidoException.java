package br.gov.caixa.caixaverso.exception;

import br.gov.caixa.caixaverso.enums.TipoProduto;

public class TipoProdutoInvalidoException extends RuntimeException {
    public TipoProdutoInvalidoException(String texto) {
        super("Produto do tipo" + texto + "inválido, \n Produtos disponíveis: " + TipoProduto.tipoProdutosDisponiveis());
    }
}
