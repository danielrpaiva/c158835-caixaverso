package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.TelemetriaSemIntervaloValidoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TelemetriaSemIntervaloValidoExceptionMapper implements ExceptionMapper<TelemetriaSemIntervaloValidoException> {

    @Override
    public Response toResponse(TelemetriaSemIntervaloValidoException exception) {

        RespostaExceptionDto respostaExceptionDto = new RespostaExceptionDto(
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(respostaExceptionDto)
                       .build();
    }
}
