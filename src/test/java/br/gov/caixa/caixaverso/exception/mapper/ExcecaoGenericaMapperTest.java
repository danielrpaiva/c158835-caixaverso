package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ExcecaoGenericaMapperTest {

    private final ExcecaoGenericaMapper mapper = new ExcecaoGenericaMapper();

    @Test
    void testExcecaoGenericaMapperToResponse(){
        Exception e = new RuntimeException("Erro generico");

        try (Response response = mapper.toResponse(e)) {
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            assertInstanceOf(RespostaExceptionDto.class, response.getEntity());
        }
    }
}
