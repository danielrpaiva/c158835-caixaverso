package br.gov.caixa.caixaverso.filter;

import br.gov.caixa.caixaverso.model.Telemetria;
import br.gov.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
@ApplicationScoped
public class TelemetriaFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final ThreadLocal<Long> inicioRequest = new ThreadLocal<>();
    private final TelemetriaRepository telemetriaRepository;

    public TelemetriaFilter(TelemetriaRepository telemetriaRepository) {
        this.telemetriaRepository = telemetriaRepository;
    }

    @Override
    public void filter(ContainerRequestContext contextoRequest) {
        inicioRequest.set(System.currentTimeMillis());
    }

    @Override
    @Transactional
    public void filter(ContainerRequestContext contextoRequest, ContainerResponseContext contextoResponse){
        Long tempoInicio = inicioRequest.get();
        Long duracao = System.currentTimeMillis() - tempoInicio;

        Telemetria telemetria = new Telemetria();
        telemetria.setNomeServico(contextoRequest.getUriInfo().getPath());
        telemetria.setMetodoHttp(contextoRequest.getMethod());
        telemetria.setStatusHttp(contextoResponse.getStatus());
        telemetria.setTempoRespostaMs(duracao);
        telemetria.setDataChamada(LocalDateTime.now());

        telemetriaRepository.persist(telemetria);

        inicioRequest.remove();
    }

}
