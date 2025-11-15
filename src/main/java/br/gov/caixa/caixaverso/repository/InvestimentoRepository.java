package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.model.Investimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvestimentoRepository implements PanacheRepository<Investimento> {
}
