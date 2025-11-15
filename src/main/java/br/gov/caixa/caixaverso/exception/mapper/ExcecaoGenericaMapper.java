package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExcecaoGenericaMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        RespostaExceptionDto respostaExceptionDto = new RespostaExceptionDto(
            "Ocorreu um erro inesperado: " + exception.getMessage(),
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(respostaExceptionDto)
                       .build();
    }
}
