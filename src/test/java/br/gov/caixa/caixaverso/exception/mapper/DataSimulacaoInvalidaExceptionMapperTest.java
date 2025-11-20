package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.DataSimulacaoInvalidaException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DataSimulacaoInvalidaExceptionMapperTest {

    private final DataSimulacaoInvalidaExceptionMapper mapper = new DataSimulacaoInvalidaExceptionMapper();

    @Test
    void testDataSimulacaoInvalidaExceptionMapperToResponse(){
        DataSimulacaoInvalidaException e = new DataSimulacaoInvalidaException();

        try (Response response = mapper.toResponse(e)) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
            assertInstanceOf(RespostaExceptionDto.class, response.getEntity());
        }
    }
}