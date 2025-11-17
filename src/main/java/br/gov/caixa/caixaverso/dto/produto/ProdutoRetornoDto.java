package br.gov.caixa.caixaverso.dto.produto;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRetornoDto {
    private Long id;

    private String nome;

    private TipoProduto tipo;

    private Double rentabilidadeAnual;

    private NivelRisco risco;
}
