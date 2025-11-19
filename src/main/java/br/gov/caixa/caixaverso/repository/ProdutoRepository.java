package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> buscarPorRisco(NivelRisco risco) {
        return list("risco", risco);
    }

    public List<Produto> buscarPorPontuacao(Integer pontuacao, Integer margem) {
        return list("pontuacaoIdeal BETWEEN ?1 and ?2", pontuacao - margem, pontuacao + margem);
    }

    public List<Produto> buscarPorTipo(TipoProduto tipoProduto) {
        return list("tipo", tipoProduto);
    }
}
