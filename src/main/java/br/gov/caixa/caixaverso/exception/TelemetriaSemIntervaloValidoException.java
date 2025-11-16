package br.gov.caixa.caixaverso.exception;

public class TelemetriaSemIntervaloValidoException extends RuntimeException {
    public TelemetriaSemIntervaloValidoException() {
        super(
                "Data de início e data de fim são obrigatórias no formato yyyy-MM-dd e " +
                        "a data de início deve ser anterior à data de fim."
        );
    }
}
