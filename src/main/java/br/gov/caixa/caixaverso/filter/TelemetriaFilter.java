package br.gov.caixa.caixaverso.filter;

import br.gov.caixa.caixaverso.model.Telemetria;
import br.gov.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Provider
@ApplicationScoped
public class TelemetriaFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final ThreadLocal<Long> inicioRequest = new ThreadLocal<>();
    private final TelemetriaRepository telemetriaRepository;
    private final ResourceInfo resourceInfo;

    public TelemetriaFilter(TelemetriaRepository telemetriaRepository, ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
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
        telemetria.setNomeServico(getPathTemplate(contextoRequest));
        telemetria.setMetodoHttp(contextoRequest.getMethod());
        telemetria.setStatusHttp(contextoResponse.getStatus());
        telemetria.setTempoRespostaMs(duracao);
        telemetria.setDataChamada(LocalDateTime.now());

        telemetriaRepository.persist(telemetria);

        inicioRequest.remove();
    }

    // Metodo auxiliar para obter o template do endpoint, para evitar que path params causem
    // a telemetria interpretar o mesmo endpoint como diferentes por causa desses parametros
    private String getPathTemplate(ContainerRequestContext contextoRequest) {
        Class<?> resourceClass = resourceInfo.getResourceClass();
        Method resourceMethod = resourceInfo.getResourceMethod();

        if (resourceClass == null || resourceMethod == null) {
            return contextoRequest.getUriInfo().getPath();
        }

        Path classPathAnnotation = resourceClass.getAnnotation(Path.class);

        String classPath = (classPathAnnotation != null) ? classPathAnnotation.value() : "";

        Path methodPathAnnotation = resourceMethod.getAnnotation(Path.class);
        String methodPath = (methodPathAnnotation != null) ? methodPathAnnotation.value() : "";

        String fullPath = "/" + classPath + "/" + methodPath;
        String cleanedPath = fullPath
                .replaceAll("//+", "/")
                .replaceAll("/$", "");

        return cleanedPath.isEmpty() ? "/" : cleanedPath;
    }
}
