package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.PerfilCliente;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keycloakId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer pontuacao;

    @Column(nullable = false)
    private PerfilCliente perfil;

    // Esses campos são usados para performance, pois eu apenas incremento novos investimentos sem precisar recalcular tudo para definir o perfil
    @Column(nullable = false)
    private Double saldoRiscoBaixo = 0.0;

    @Column(nullable = false)
    private Double saldoRiscoMedio = 0.0;

    @Column(nullable = false)
    private Double saldoRiscoAlto = 0.0;

    public Double getTotalInvestido() {
        return saldoRiscoBaixo + saldoRiscoMedio + saldoRiscoAlto;
    }
}
