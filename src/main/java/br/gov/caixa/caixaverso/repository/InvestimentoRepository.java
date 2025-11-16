package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Investimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class InvestimentoRepository implements PanacheRepository<Investimento> {

    public List<Investimento> buscarPorCliente(Cliente cliente) {
        return list("cliente", cliente);
    }

    public Long countPorClienteEData(Cliente cliente, LocalDateTime dataLimite) {
        return count("cliente = ?1 and dataInvestimento > ?2", cliente, dataLimite);
    }
}
