package br.gov.caixa.caixaverso.exception;

public class DataSimulacaoInvalidaException extends RuntimeException {
    public DataSimulacaoInvalidaException() {
        super("Data não está no formato yyyy-MM-dd, insira uma data válida");
    }
}
