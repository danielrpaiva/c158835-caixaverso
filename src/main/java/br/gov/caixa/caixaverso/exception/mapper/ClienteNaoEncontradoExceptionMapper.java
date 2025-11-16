package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClienteNaoEncontradoExceptionMapper implements ExceptionMapper<ClienteNaoEncontradoException> {

    @Override
    public Response toResponse(ClienteNaoEncontradoException exception) {

        RespostaExceptionDto respostaExceptionDto = new RespostaExceptionDto(
            exception.getMessage(),
            Response.Status.NOT_FOUND.getStatusCode()
        );

        return Response.status(Response.Status.NOT_FOUND)
                       .entity(respostaExceptionDto)
                       .build();
    }

}
