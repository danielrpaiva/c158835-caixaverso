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
    private Integer pontuacaoRisco;

    @Column(nullable = false)
    private PerfilCliente perfil;
}
