package br.gov.caixa.caixaverso.dto.cliente;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrarClienteDto {

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    private String sobrenome;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String senha;

}
