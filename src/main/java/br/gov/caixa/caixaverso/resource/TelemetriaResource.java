package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.telemetria.TelemetriaRetornoDto;
import br.gov.caixa.caixaverso.service.TelemetriaService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("api/telemetria")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class TelemetriaResource {

    private final TelemetriaService telemetriaService;

    public TelemetriaResource(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    // 4. Dados de Telemetria
    @Operation(
            summary = "4. Dados de Telemetria",
            description = "Endpoint 4 do enunciado"
    )
    @GET
    @Authenticated
    public Response obterDadosTelemetria(
            @QueryParam("inicio") String inicio,
            @QueryParam("fim") String fim) {

        return Response.ok(telemetriaService.obterDadosTelemetria(inicio, fim)).build();
    }
}
