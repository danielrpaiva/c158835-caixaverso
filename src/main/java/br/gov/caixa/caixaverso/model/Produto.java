package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private TipoProduto tipo;

    @Column(nullable = false)
    private Double rentabilidadeAnual;

    @Column(nullable = false)
    private NivelRisco risco;

    @Column(nullable = false)
    private Integer pontuacaoIdeal;
}
