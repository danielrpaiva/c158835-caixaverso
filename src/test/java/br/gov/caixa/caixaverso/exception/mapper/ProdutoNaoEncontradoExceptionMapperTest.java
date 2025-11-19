package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.ProdutoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ProdutoNaoEncontradoExceptionMapperTest {

    private final ProdutoNaoEncontradoExceptionMapper mapper = new ProdutoNaoEncontradoExceptionMapper();

    @Test
    void testProdutoNaoEncontradoExceptionMapperToResponse(){
        ProdutoNaoEncontradoException e = new ProdutoNaoEncontradoException(20L);

        try (Response response = mapper.toResponse(e)) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
            assertInstanceOf(RespostaExceptionDto.class, response.getEntity());
        }
    }
}