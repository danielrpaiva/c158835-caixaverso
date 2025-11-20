package br.gov.caixa.caixaverso.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Telemetria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeServico;

    @Column(nullable = false)
    private String metodoHttp;

    @Column(nullable = false)
    private Integer statusHttp;

    @Column(nullable = false)
    private Long tempoRespostaMs;

    @Column(nullable = false)
    private LocalDateTime dataChamada;

}
