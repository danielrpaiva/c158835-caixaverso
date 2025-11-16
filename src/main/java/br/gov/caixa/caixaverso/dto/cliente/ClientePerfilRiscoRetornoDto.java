package br.gov.caixa.caixaverso.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientePerfilRiscoRetornoDto {
    private Long clienteId;
    private String perfil;
    private Integer pontuacao;
    private String descricao;
}
