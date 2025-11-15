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

        if (filtro.clienteId != null) {
            query += " AND cliente.id = :clienteId";
            params.put("clienteId", filtro.clienteId);
        }

        if (filtro.produtoId != null) {
            query += " AND produto.id = :produtoId";
            params.put("produtoId", filtro.produtoId);
        }

        if (filtro.produtoNome != null && !filtro.produtoNome.isBlank()) {
            query += " AND produto.nome LIKE :produtoNome";
            params.put("produtoNome", "%" + filtro.produtoNome + "%");
        }

        if (filtro.dataInicio != null) {
            query += " AND dataSimulacao >= :dataInicio";
            params.put("dataInicio", filtro.dataInicio);
        }

        if (filtro.dataFim != null) {
            query += " AND dataSimulacao <= :dataFim";
            params.put("dataFim", filtro.dataFim);
        }

        return find(query, params).list();
    }
}
