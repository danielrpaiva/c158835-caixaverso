package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.repository.InvestimentoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MotorRecomendacaoService {

    private final InvestimentoRepository investimentoRepository;

    public MotorRecomendacaoService(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    /*
    * Como definido no enunciado, usei três critérios para recomendar produtos financeiros aos clientes
    * Critério 1. Preferência por liquidez ou rentabilidade
    * Critério 2. Frequência de movimentação
    * Critério 3. Volume de investimentos (esse critério se correlaciona com o critério 1, pois vejo o volume para
    * cada tipo de produto)
    */

    /* Método para definir o perfil do cliente com base
     * nas porcentagens de volume de investimento dele de risco baixo, médio e alto
     */
    public PerfilCliente definirPerfil(Cliente cliente) {

        double totalInvestido = cliente.getTotalInvestido();

        if (totalInvestido <= 0.001) {
            return PerfilCliente.CONSERVADOR;
        }

        double porcentagemBaixo = cliente.getSaldoRiscoBaixo() / totalInvestido;
        //double porcentagemMedio = cliente.getSaldoRiscoMedio() / totalInvestido;
        double porcentagemAlto = cliente.getSaldoRiscoAlto() / totalInvestido;

        // Se tem mais de 85% em baixo risco = CONSERVADOR
        if (porcentagemBaixo >= 0.85) {
            return PerfilCliente.CONSERVADOR;
        }

        // Se tem mais de 35% em alto risco e menos de 50% em baixo risco OU mais de 45% em alto risco = AGGRESIVO
        else if (porcentagemAlto >= 0.45 || porcentagemAlto >= 0.35 && porcentagemBaixo < 0.50) {
            return PerfilCliente.AGGRESIVO;
        }

        else {
            return PerfilCliente.MODERADO;
        }
    }
}
