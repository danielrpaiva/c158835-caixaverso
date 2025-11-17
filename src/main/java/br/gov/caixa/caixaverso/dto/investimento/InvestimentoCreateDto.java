package br.gov.caixa.caixaverso.dto.investimento;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoCreateDto {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long produtoId;

    @NotNull
    private Double valor;

    @NotNull
    private Double rentabilidade;
}
