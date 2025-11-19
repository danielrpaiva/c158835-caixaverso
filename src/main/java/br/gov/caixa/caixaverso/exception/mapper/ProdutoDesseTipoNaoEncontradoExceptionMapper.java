package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.ProdutoDesseTipoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ProdutoDesseTipoNaoEncontradoExceptionMapper implements ExceptionMapper<ProdutoDesseTipoNaoEncontradoException> {

    @Override
    public Response toResponse(ProdutoDesseTipoNaoEncontradoException exception) {

        RespostaExceptionDto respostaExceptionDto = new RespostaExceptionDto(
                exception.getMessage(),
                Response.Status.NOT_FOUND.getStatusCode()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(respostaExceptionDto)
                .build();
    }
}
