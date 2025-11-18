package br.gov.caixa.caixaverso.dto.simulacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoCreateDto {

    @NotNull
    private Long clienteId;

    @NotNull
    @Positive(message = "O valor deve ser um número positivo.")
    private Double valor;

    @NotNull
    @Positive(message = "O prazo de meses deve ser um número positivo.")
    private Integer prazoMeses;

    @NotBlank
    private String tipoProduto;

}
