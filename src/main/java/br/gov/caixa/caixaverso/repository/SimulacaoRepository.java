package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoFiltroDto;
import br.gov.caixa.caixaverso.model.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {

    public List<Simulacao> buscarComFiltro(SimulacaoFiltroDto filtro) {

        String query = "TRUE";
        Map<String, Object> params = new HashMap<>();

        if (filtro.getClienteId() != null) {
            query += " AND cliente.id = :clienteId";
            params.put("clienteId", filtro.getClienteId());
        }

        if (filtro.getProdutoId() != null) {
            query += " AND produto.id = :produtoId";
            params.put("produtoId", filtro.getProdutoId());
        }

        if (filtro.getProdutoNome() != null && !filtro.getProdutoNome().isBlank()) {
            query += " AND produto.nome LIKE :produtoNome";
            params.put("produtoNome", "%" + filtro.getProdutoNome() + "%");
        }

        if (filtro.getDataInicio() != null) {
            query += " AND dataSimulacao >= :dataInicio";
            params.put("dataInicio", filtro.getDataInicio());
        }

        if (filtro.getDataFim() != null) {
            query += " AND dataSimulacao <= :dataFim";
            params.put("dataFim", filtro.getDataFim());
        }

        return find(query, params).list();
    }
}
