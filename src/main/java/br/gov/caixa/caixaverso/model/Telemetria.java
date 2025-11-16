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
    public String nomeServico;

    @Column(nullable = false)
    public String metodoHttp;

    @Column(nullable = false)
    public Integer statusHttp;

    @Column(nullable = false)
    public Long tempoRespostaMs;

    @Column(nullable = false)
    public LocalDateTime dataChamada;

}
