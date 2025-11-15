package br.gov.caixa.caixaverso.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespostaExceptionDto {
    private String mensagem;
    private Integer status;
}
