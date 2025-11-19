package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ProdutoDesseTipoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ProdutoDesseTipoNaoEncontradoExceptionMapperTest {

    private final ProdutoDesseTipoNaoEncontradoExceptionMapper mapper = new ProdutoDesseTipoNaoEncontradoExceptionMapper();

    @Test
    void testProdutoDesseTipoNaoEncontradoExceptionMapperToResponse(){
        ProdutoDesseTipoNaoEncontradoException e = new ProdutoDesseTipoNaoEncontradoException(TipoProduto.CDB);

        try (Response response = mapper.toResponse(e)) {
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
            assertInstanceOf(RespostaExceptionDto.class, response.getEntity());
        }
    }
}