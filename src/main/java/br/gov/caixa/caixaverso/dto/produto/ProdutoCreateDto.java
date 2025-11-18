package br.gov.caixa.caixaverso.dto.produto;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDto {

    @NotBlank
    private String nome;

    @NotNull
    private TipoProduto tipo;

    @NotNull
    @Positive
    private Double rentabilidadeAnual;

    @NotNull
    private NivelRisco risco;

    @NotNull
    @PositiveOrZero
    private Integer pontuacaoIdeal;
}
