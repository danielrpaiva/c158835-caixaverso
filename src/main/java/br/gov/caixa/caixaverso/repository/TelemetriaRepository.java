package br.gov.caixa.caixaverso.repository;

import br.gov.caixa.caixaverso.model.Telemetria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TelemetriaRepository implements PanacheRepository<Telemetria> {

    public List<Telemetria> buscarPorData(LocalDateTime inicio, LocalDateTime fim) {
        return list(
                "dataChamada >= :inicio and dataChamada <= :fim",
                Parameters.with("inicio", inicio).and("fim", fim)
        );
    }
}
