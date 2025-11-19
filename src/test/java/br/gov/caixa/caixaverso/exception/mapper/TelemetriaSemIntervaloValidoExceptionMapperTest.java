package br.gov.caixa.caixaverso.exception.mapper;

import br.gov.caixa.caixaverso.dto.RespostaExceptionDto;
import br.gov.caixa.caixaverso.exception.TelemetriaSemIntervaloValidoException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TelemetriaSemIntervaloValidoExceptionMapperTest {

    private final TelemetriaSemIntervaloValidoExceptionMapper mapper = new TelemetriaSemIntervaloValidoExceptionMapper();

    @Test
    void testTelemetriaSemIntervaloValidoExceptionMapperToResponse(){
        TelemetriaSemIntervaloValidoException e = new TelemetriaSemIntervaloValidoException();

        try (Response response = mapper.toResponse(e)) {
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
            assertInstanceOf(RespostaExceptionDto.class, response.getEntity());
        }
    }
}