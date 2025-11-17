package br.gov.caixa.caixaverso.dto.produto;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Double rentabilidadeAnual;

    @NotNull
    private NivelRisco risco;

    @NotNull
    private Integer pontuacaoIdeal;
}
