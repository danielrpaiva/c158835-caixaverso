package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
}
