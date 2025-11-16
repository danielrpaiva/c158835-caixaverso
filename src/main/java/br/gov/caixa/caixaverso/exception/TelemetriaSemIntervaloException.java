package br.gov.caixa.caixaverso.exception;

public class TelemetriaSemIntervaloException extends RuntimeException {
    public TelemetriaSemIntervaloException() {
        super("Data de início e data de fim são obrigatórias para consulta de telemetria.");
    }
}
