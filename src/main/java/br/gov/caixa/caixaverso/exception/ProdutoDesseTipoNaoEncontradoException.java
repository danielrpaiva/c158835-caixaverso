package br.gov.caixa.caixaverso.exception;

import br.gov.caixa.caixaverso.enums.TipoProduto;

public class ProdutoDesseTipoNaoEncontradoException extends RuntimeException {
    public ProdutoDesseTipoNaoEncontradoException(TipoProduto tipoProduto) {
        super("Não foi encontrado nenhum produto do tipo" + tipoProduto.getNome());
    }
}
