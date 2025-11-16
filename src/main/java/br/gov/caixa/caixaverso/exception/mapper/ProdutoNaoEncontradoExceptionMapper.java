package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.exception.ProdutoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProdutoNaoEncontradoExceptionMapper implements ExceptionMapper<ProdutoNaoEncontradoException> {

    @Override
    public Response toResponse(ProdutoNaoEncontradoException exception) {

        RespostaExceptionDto respostaExceptionDto = new RespostaExceptionDto(
                exception.getMessage(),
                Response.Status.NOT_FOUND.getStatusCode()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(respostaExceptionDto)
                .build();
    }
}
